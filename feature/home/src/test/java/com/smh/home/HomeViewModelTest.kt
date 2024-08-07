package com.smh.home

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isGreaterThan
import com.smh.home.domain.usecase.CalculatePortfolioStatisticUseCase
import com.smh.home.domain.usecase.CombineCoinsWithPortfoliosUseCase
import com.smh.home.domain.usecase.GetAllPortfolioUseCase
import com.smh.home.domain.usecase.GetCoinsUseCase
import com.smh.home.domain.usecase.GetGlobalStatisticsUseCase
import com.smh.home.domain.usecase.MergeAndSortStatisticsUseCase
import com.smh.home.domain.usecase.UpdatePortfolioUseCase
import com.smh.home.fake.HomeRepositoryFake
import com.smh.home.fake.btc
import com.smh.home.fake.btcEntity
import com.smh.home.fake.eth
import com.smh.home.helper.TestCoroutineExtension
import com.smh.home.ui.HomeViewModel
import com.smh.home.ui.SortOption
import com.smh.home.utility.CoinSorterAndFilter
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class HomeViewModelTest {

    private lateinit var homeRepository: HomeRepositoryFake
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var getGlobalStatisticsUseCase: GetGlobalStatisticsUseCase
    private lateinit var getAllPortfolioUseCase: GetAllPortfolioUseCase
    private lateinit var updatePortfolioUseCase: UpdatePortfolioUseCase
    private lateinit var mergeAndSortStatisticsUseCase: MergeAndSortStatisticsUseCase
    private lateinit var calculatePortfolioStatisticUseCase: CalculatePortfolioStatisticUseCase
    private lateinit var combineCoinsWithPortfoliosUseCase: CombineCoinsWithPortfoliosUseCase
    private lateinit var coinSorterAndFilter: CoinSorterAndFilter

    @BeforeEach
    fun setup() {
        homeRepository = HomeRepositoryFake()
        getCoinsUseCase = GetCoinsUseCase(StandardTestDispatcher(), homeRepository)
        getGlobalStatisticsUseCase = GetGlobalStatisticsUseCase(StandardTestDispatcher(), homeRepository)
        getAllPortfolioUseCase = GetAllPortfolioUseCase(StandardTestDispatcher(), homeRepository)
        updatePortfolioUseCase = UpdatePortfolioUseCase(StandardTestDispatcher(), homeRepository)
        mergeAndSortStatisticsUseCase = MergeAndSortStatisticsUseCase()
        calculatePortfolioStatisticUseCase = CalculatePortfolioStatisticUseCase()
        combineCoinsWithPortfoliosUseCase = CombineCoinsWithPortfoliosUseCase()
        coinSorterAndFilter = CoinSorterAndFilter()
        homeViewModel = HomeViewModel(
            StandardTestDispatcher(),
            getCoinsUseCase,
            getGlobalStatisticsUseCase,
            getAllPortfolioUseCase,
            updatePortfolioUseCase,
            mergeAndSortStatisticsUseCase,
            calculatePortfolioStatisticUseCase,
            combineCoinsWithPortfoliosUseCase,
            coinSorterAndFilter
        )
    }

    @Test
    fun `HomeViewModel UiState Test`() = runTest {
        homeViewModel.uiState.test {
            val emission1 = awaitItem()
            assertThat(emission1.loading).isFalse()

            val emission2 = awaitItem()
            assertThat(emission2.statistics.size).isGreaterThan(0)

            skipItems(2)
            val emission3 = awaitItem()
            assertThat(emission1.loading).isFalse()
            assertThat(emission3.statistics.size).isEqualTo(4)
            assertThat(emission3.liveCoins).isEqualTo(homeRepository.homeRemoteDataSource.coins)
            assertThat(emission3.portfolios).isEmpty()

            homeRepository.insertPortfolio(entity = btcEntity)
            skipItems(2)
            val emission4 = awaitItem()
            assertThat(emission4.portfolios.size).isEqualTo(1)
            assertThat(emission4.portfolios.first().holdingAmount).isEqualTo(btcEntity.amount)

            homeViewModel.onSearchQueryChange(btc.id)
            skipItems(1)
            val emission5 = awaitItem()
            assertThat(emission5.liveCoins.size).isEqualTo(1)
            assertThat(emission5.liveCoins.first().id).isEqualTo(btc.id)

            homeViewModel.onSearchQueryChange(eth.id)
            skipItems(1)
            val emission6 = awaitItem()
            assertThat(emission6.liveCoins.size).isEqualTo(1)
            assertThat(emission6.liveCoins.first().id).isEqualTo(eth.id)

            homeViewModel.onSearchQueryChange("nothing")
            skipItems(1)
            val emission7 = awaitItem()
            assertThat(emission7.liveCoins).isEmpty()

            homeViewModel.onSearchQueryChange("")
            skipItems(1)

            homeViewModel.onSortChange(sortOption = SortOption.Price)
            val emission8 = awaitItem()
            assertThat(emission8.liveCoins.first().id).isEqualTo(eth.id)

            homeViewModel.onSortChange(sortOption = SortOption.PriceReverse)
            val emission9 = awaitItem()
            assertThat(emission9.liveCoins.first().id).isEqualTo(btc.id)

            homeViewModel.onSortChange(sortOption = SortOption.Rank)
            val emission10 = awaitItem()
            assertThat(emission10.liveCoins.first().id).isEqualTo(btc.id)

            homeViewModel.onSortChange(sortOption = SortOption.RankReverse)
            val emission11 = awaitItem()
            assertThat(emission11.liveCoins.first().id).isEqualTo(eth.id)

            cancelAndIgnoreRemainingEvents()
        }
    }
}