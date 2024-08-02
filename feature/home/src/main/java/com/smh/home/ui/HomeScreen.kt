package com.smh.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smh.design.CTAnimatedContent
import com.smh.design.CoinTrackerLoading
import com.smh.design.SearchBar
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.home.domain.model.CoinModel
import com.smh.home.ui.components.HomeCoinListHeaders
import com.smh.home.ui.components.HomeCoinsList
import com.smh.home.ui.components.HomeHeader
import com.smh.home.ui.components.HomeStatistics
import com.smh.home.ui.sheet.EditPortfolioSheet
import com.smh.home.ui.sheet.SettingsSheet

@Composable
fun HomeScreen(
    goToDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onUserEvent = {
            when(it) {
                is HomeUserEvent.OnSortChange -> viewModel.onSortChange(it.sortOption)
                is HomeUserEvent.OnSearchQueryChange -> viewModel.onSearchQueryChange(it.query)
                is HomeUserEvent.OnUpdatePortfolio -> viewModel.onUpdatePortfolio(it.coin, it.amount)
                is HomeUserEvent.OnCoinClick -> goToDetails(it.coinId)
            }
        }
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onUserEvent: (HomeUserEvent) -> Unit
) {
    var contentState by remember { mutableStateOf(HomeContentState.LivePrices) }

    var showEditPortfolioSheet by remember { mutableStateOf(false) }
    EditPortfolioSheet(
        show = showEditPortfolioSheet,
        coins = uiState.allCoins,
        portfolios = uiState.allPortfolios,
        onDismiss = { showEditPortfolioSheet = false },
        onSave = { coin, amount -> onUserEvent(HomeUserEvent.OnUpdatePortfolio(coin, amount)) }
    )

    var showSettingsSheet by remember { mutableStateOf(false) }
    SettingsSheet(
        show = showSettingsSheet,
        onDismiss = { showSettingsSheet = false }
    )

    if (uiState.loading) CoinTrackerLoading()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HomeHeader(
                contentState = contentState,
                onShowLivePricesChange = { contentState = it },
                onInfoClick = { showSettingsSheet = true },
                onEditPortfolioClick = { showEditPortfolioSheet = true }
            )

            HomeStatistics(
                contentState = contentState,
                statistics = uiState.statistics
            )

            SearchBar(
                value = uiState.searchQuery,
                onClear = { onUserEvent(HomeUserEvent.OnSearchQueryChange("")) },
                onValueChanged = { onUserEvent(HomeUserEvent.OnSearchQueryChange(it)) },
                placeholder = "Search symbol or name",
                modifier = Modifier
                    .padding(
                        horizontal = dimensions.horizontalSpace,
                        vertical = dimensions.space24
                    )
            )

            HomeCoinListHeaders(
                contentState = contentState,
                sortOption = uiState.sortOption,
                onSortChange = { onUserEvent(HomeUserEvent.OnSortChange(it)) }
            )

            CTAnimatedContent(
                targetState = contentState,
                modifier = Modifier.weight(1f)
            ) { state ->
                when (state) {
                    HomeContentState.LivePrices -> HomeCoinsList(
                        coins = uiState.liveCoins,
                        showHolding = false,
                        onClick = { onUserEvent(HomeUserEvent.OnCoinClick(it)) }
                    )

                    HomeContentState.Portfolio -> HomeCoinsList(
                        coins = uiState.portfolios,
                        showHolding = true,
                        onClick = { onUserEvent(HomeUserEvent.OnCoinClick(it)) }
                    )
                }
            }
        }
    }
}

sealed interface HomeUserEvent {
    data class OnSortChange(val sortOption: SortOption): HomeUserEvent
    data class OnSearchQueryChange(val query: String): HomeUserEvent
    data class OnUpdatePortfolio(val coin: CoinModel, val amount: Double): HomeUserEvent
    data class OnCoinClick(val coinId: String): HomeUserEvent
}

@DayPreview
@Composable
private fun HomeContentPreview() {
    CoinTrackerTheme {
        HomeContent(
            uiState = HomeUiState(),
            onUserEvent = {}
        )
    }
}

