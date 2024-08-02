package com.smh.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smh.design.MessageBar
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.usecase.CalculatePortfolioStatisticUseCase
import com.smh.home.domain.usecase.CombineCoinsWithPortfoliosUseCase
import com.smh.home.domain.usecase.GetAllPortfolioUseCase
import com.smh.home.domain.usecase.GetCoinsUseCase
import com.smh.home.domain.usecase.GetGlobalStatisticsUseCase
import com.smh.home.domain.usecase.MergeAndSortStatisticsUseCase
import com.smh.home.domain.usecase.UpdatePortfolioUseCase
import com.smh.home.ui.model.StatisticModel
import com.smh.home.utility.CoinSorterAndFilter
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.executeEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getGlobalStatisticsUseCase: GetGlobalStatisticsUseCase,
    private val getAllPortfolioUseCase: GetAllPortfolioUseCase,
    private val updatePortfolioUseCase: UpdatePortfolioUseCase,
    private val mergeAndSortStatisticsUseCase: MergeAndSortStatisticsUseCase,
    private val calculatePortfolioStatisticUseCase: CalculatePortfolioStatisticUseCase,
    private val combineCoinsWithPortfoliosUseCase: CombineCoinsWithPortfoliosUseCase,
    private val coinSorterAndFilter: CoinSorterAndFilter,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val loadingUiState = MutableStateFlow(HomeLoadingUiState())

    init {
        fetchGlobal()
        fetchLiveCoins()
        observeSearchQuery()
        observeAllLiveCoin()
        observeAllPortfolioCoin()
        observeLoading()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch(dispatcher) {
            uiState
                .map { it.searchQuery }
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    _uiState.update {
                        it.copy(
                            liveCoins = coinSorterAndFilter.filterAndSortLiveCoins(
                                query = query,
                                sortOption = it.sortOption,
                                allCoins = it.allCoins
                            ),
                            portfolios = coinSorterAndFilter.filterAndSortPortfolioCoins(
                                query = query,
                                sortOption = it.sortOption,
                                allCoins = it.allPortfolios
                            )
                        )
                    }
                }
        }
    }

    private fun observeAllLiveCoin() {
        viewModelScope.launch(dispatcher) {
            uiState
                .map { it.allCoins }
                .combine(
                    getAllPortfolioUseCase.executeEmpty(),
                    combineCoinsWithPortfoliosUseCase::invoke
                )
                .distinctUntilChanged()
                .collectLatest { data ->
                    _uiState.getAndUpdate {
                        it.copy(
                            portfolios = coinSorterAndFilter.sortPortfolioCoins(
                                sortOption = it.sortOption,
                                data = data
                            ),
                            allPortfolios = data
                        )
                    }
                }
        }
    }

    private fun observeAllPortfolioCoin() {
        viewModelScope.launch(dispatcher) {
            uiState.map { it.allPortfolios }
                .map { calculatePortfolioStatisticUseCase(it) }
                .distinctUntilChanged()
                .collectLatest { data ->
                    _uiState.getAndUpdate {
                        it.copy(
                            statistics = mergeAndSortStatisticsUseCase(
                                it.statistics,
                                listOf(data)
                            )
                        )
                    }
                }
        }
    }

    private fun observeLoading() {
        viewModelScope.launch(dispatcher) {
            loadingUiState
                .map { it.global || it.liveCoins }
                .collectLatest { loading ->
                    _uiState.update {
                        it.copy(
                            loading = loading
                        )
                    }
                }
        }
    }

    private fun fetchLiveCoins() {
        setLiveCoinLoading(true)
        viewModelScope.launch(dispatcher) {
            getCoinsUseCase.executeEmpty()
                .tap { data ->
                    setLiveCoinLoading(false)
                    _uiState.getAndUpdate { uiState ->
                        uiState.copy(
                            allCoins = data,
                            liveCoins = coinSorterAndFilter.sortLiveCoins(uiState.sortOption, data)
                        )
                    }
                }
                .tapLeft { error ->
                    Log.e("HOME", error.message.toString())
                    setLiveCoinLoading(false)
                    MessageBar.showMessage(error.message.toString())
                }
        }
    }

    private fun fetchGlobal() {
        setGlobalLoading(true)
        viewModelScope.launch(dispatcher) {
            getGlobalStatisticsUseCase.executeEmpty()
                .tap { data ->
                    setGlobalLoading(false)
                    _uiState.getAndUpdate {
                        it.copy(
                            statistics = mergeAndSortStatisticsUseCase(
                                it.statistics,
                                data
                            )
                        )
                    }
                }
                .tapLeft { error ->
                    Log.e("HOME", error.message.toString())
                    setGlobalLoading(false)
                    MessageBar.showMessage(error.message.toString())
                }
        }
    }

    fun onSortChange(sortOption: SortOption) {
        _uiState.getAndUpdate { uiState ->
            uiState.copy(
                sortOption = sortOption,
                liveCoins = coinSorterAndFilter.filterAndSortLiveCoins(
                    query = uiState.searchQuery,
                    sortOption = sortOption,
                    allCoins = uiState.allCoins
                ),
                portfolios = coinSorterAndFilter.filterAndSortPortfolioCoins(
                    query = uiState.searchQuery,
                    sortOption = sortOption,
                    allCoins = uiState.allPortfolios
                )
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    fun onUpdatePortfolio(coin: CoinModel, amount: Double) {
        viewModelScope.launch {
            updatePortfolioUseCase.execute(Pair(coin, amount))
        }
    }

    private fun setLiveCoinLoading(loading: Boolean) {
        loadingUiState.update {
            it.copy(
                liveCoins = loading,
            )
        }
    }

    private fun setGlobalLoading(loading: Boolean) {
        loadingUiState.update {
            it.copy(
                global = loading,
            )
        }
    }
}

enum class SortOption {
    Rank, Holding, Price, RankReverse, HoldingReverse, PriceReverse
}

private data class HomeLoadingUiState(
    val global: Boolean = false,
    val liveCoins: Boolean = false,
)

data class HomeUiState(
    val loading: Boolean = false,
    val searchQuery: String = "",
    val allCoins: List<CoinModel> = emptyList(),
    val liveCoins: List<CoinModel> = emptyList(),
    val allPortfolios: List<CoinModel> = emptyList(),
    val portfolios: List<CoinModel> = emptyList(),
    val sortOption: SortOption = SortOption.Rank,
    val statistics: List<StatisticModel> = emptyList()
)