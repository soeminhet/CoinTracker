package com.smh.home.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.domain.model.CoinModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PortfolioSheetCoinList(
    coins: List<CoinModel>,
    selectedCoin: CoinModel?,
    onCoinSelected: (CoinModel) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensions.space12),
        contentPadding = PaddingValues(start = dimensions.horizontalSpace),
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
    ) {
        items(
            items = coins,
            key = { it.id }
        ) {
            PortfolioSheetCoinLogo(
                data = it,
                isSelected = { it.id == selectedCoin?.id },
                onClick = { coin -> onCoinSelected(coin) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@DayPreview
@Composable
private fun PortfolioSheetCoinListPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetCoinList(
                coins = listOf(CoinModel.btc),
                selectedCoin = CoinModel.btc,
                onCoinSelected = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun PortfolioSheetCoinListNightPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetCoinList(
                coins = listOf(CoinModel.btc),
                selectedCoin = null,
                onCoinSelected = {}
            )
        }
    }
}