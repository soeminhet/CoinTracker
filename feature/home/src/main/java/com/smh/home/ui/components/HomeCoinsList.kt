package com.smh.home.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.smh.design.extension.noRippleClick
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.R
import com.smh.home.domain.model.CoinModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCoinsList(
    modifier: Modifier = Modifier,
    coins: List<CoinModel>,
    showHolding: Boolean,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .animateContentSize()
            .fillMaxSize()
            .testTag(stringResource(id = R.string.homeCoinList_test_tag))
    ) {
        items(
            items = coins,
            key = { it.id }
        ) { coin ->
            Column(
                modifier = Modifier
                    .animateItemPlacement()
                    .noRippleClick { onClick(coin.id) }
            ) {
                HomeCoinItem(
                    data = coin,
                    showHolding = showHolding,
                    modifier = Modifier.padding(horizontal = dimensions.horizontalSpace)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = dimensions.space12)
                        .padding(start = dimensions.horizontalSpace)
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun HomeCoinsListPreview() {
    CoinTrackerTheme {
        Surface {
            HomeCoinsList(
                coins = listOf(CoinModel.btc),
                showHolding = false,
                onClick = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun HomeCoinsListNightPreview() {
    CoinTrackerTheme {
        Surface {
            HomeCoinsList(
                coins = listOf(CoinModel.btc),
                showHolding = true,
                onClick = {}
            )
        }
    }
}