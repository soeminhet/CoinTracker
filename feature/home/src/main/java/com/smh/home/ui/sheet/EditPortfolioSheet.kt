package com.smh.home.ui.sheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.smh.design.CoinTrackerSheet
import com.smh.design.SearchBar
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.home.domain.model.CoinModel
import com.smh.home.ui.components.PortfolioSheetCoinDetails
import com.smh.home.ui.components.PortfolioSheetCoinList
import com.smh.home.ui.components.PortfolioSheetHeader
import com.smh.home.ui.components.PortfolioSheetTitle
import com.smh.home.utility.CoinSorterAndFilter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun EditPortfolioSheet(
    show: Boolean,
    coins: List<CoinModel>,
    portfolios: List<CoinModel>,
    onSave: (CoinModel, Double) -> Unit,
    onDismiss: () -> Unit
) {
    CoinTrackerSheet(
        show = show,
        onDismiss = onDismiss
    ) { onHide ->
        EditPortfolioSheetContent(
            onDismiss = onHide,
            coins = coins,
            portfolios = portfolios,
            onSave = onSave
        )
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun EditPortfolioSheetContent(
    onDismiss: () -> Unit,
    coins: List<CoinModel>,
    portfolios: List<CoinModel>,
    onSave: (CoinModel, Double) -> Unit
) {
    val coinSorterAndFilter by remember { mutableStateOf(CoinSorterAndFilter()) }
    var selectedCoin by remember { mutableStateOf<CoinModel?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var filteredCoins by remember { mutableStateOf(coins) }
    var amount by remember { mutableStateOf("") }
    val currentValue by remember {
        derivedStateOf {
            (selectedCoin?.price ?: 0.0) * (amount.toDoubleOrNull() ?: 0.0)
        }
    }

    var showSaveSuccess by remember { mutableStateOf(false) }
    val saveButtonAlpha by animateFloatAsState(
        targetValue = if (amount.isNotEmpty()) 1f else 0f,
        label = "PortfolioSaveButtonAlpha"
    )
    val enableSaveButton by remember {
        derivedStateOf {
            amount.isNotEmpty() && amount.toDoubleOrNull() != null
                    && selectedCoin?.holdingAmount != amount.toDoubleOrNull()
        }
    }

    LaunchedEffect(Unit) {
        launch {
            snapshotFlow { searchQuery }
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    filteredCoins = coinSorterAndFilter.filterCoins(query, coins)
                }
        }
    }

    LaunchedEffect(portfolios) {
        snapshotFlow { selectedCoin }
            .collectLatest { coin ->
                if (coin == null) { amount = "" }
                else {
                    val holdingAmount = portfolios.firstOrNull { it.id == coin.id }?.holdingAmount
                    selectedCoin = selectedCoin?.copy(holdingAmount = holdingAmount)
                    amount = (holdingAmount ?: "").toString()
                }
            }
    }

    LaunchedEffect(showSaveSuccess) {
        if (showSaveSuccess) {
            delay(3000)
            showSaveSuccess = false
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = dimensions.space12)
                .fillMaxSize()
        ) {
            PortfolioSheetHeader(
                onDismiss = onDismiss,
                showSaveSuccess = showSaveSuccess,
                saveButtonAlpha = saveButtonAlpha,
                enableSaveButton = enableSaveButton,
                onSave = { selectedCoin?.let { onSave(it, amount.toDouble()) } },
                resetState = {
                    amount = "0"
                    selectedCoin = null
                    showSaveSuccess = true
                }
            )

            PortfolioSheetTitle()

            SearchBar(
                value = searchQuery,
                placeholder = "Search symbol or name",
                onClear = { searchQuery = "" },
                onValueChanged = { value -> searchQuery = value },
                modifier = Modifier
                    .padding(
                        horizontal = dimensions.horizontalSpace,
                        vertical = dimensions.space16
                    )
            )

            PortfolioSheetCoinList(
                coins = filteredCoins,
                selectedCoin = selectedCoin,
                onCoinSelected = { coin -> selectedCoin = coin }
            )

            selectedCoin?.let { coin ->
                PortfolioSheetCoinDetails(
                    coin = coin,
                    amount = amount,
                    onAmountChanged = { amount = it },
                    currentValue = currentValue
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun EditPortfolioSheetPreview() {
    CoinTrackerTheme {
        EditPortfolioSheetContent(
            onDismiss = {},
            coins = listOf(CoinModel.btc),
            portfolios = listOf(CoinModel.btc),
            onSave = { _, _ -> }
        )
    }
}