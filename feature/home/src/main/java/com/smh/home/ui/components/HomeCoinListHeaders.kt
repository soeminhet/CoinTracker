package com.smh.home.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.smh.design.utility.CLAnimationFast
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.home.ui.HomeContentState
import com.smh.home.ui.SortOption

@Composable
fun HomeCoinListHeaders(
    contentState: HomeContentState,
    sortOption: SortOption,
    onSortChange: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = dimensions.horizontalSpace)
            .padding(bottom = dimensions.space16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeListLabel(
            text = "Coin",
            isDownSorting = sortOption == SortOption.Rank ||
                    if (contentState == HomeContentState.LivePrices) sortOption == SortOption.Holding else false,
            showSorting = sortOption == SortOption.Rank || sortOption == SortOption.RankReverse ||
                    if (contentState == HomeContentState.LivePrices) sortOption == SortOption.Holding || sortOption == SortOption.HoldingReverse else false,
            onSortingChange = { onSortChange(if (it) SortOption.Rank else SortOption.RankReverse) }
        )

        AnimatedVisibility(
            visible = contentState == HomeContentState.Portfolio,
            enter = fadeIn(tween(CLAnimationFast)),
            exit = fadeOut(tween(CLAnimationFast))
        ) {
            HomeListLabel(
                text = "Holding",
                isDownSorting = sortOption == SortOption.Holding,
                showSorting = sortOption == SortOption.Holding || sortOption == SortOption.HoldingReverse,
                onSortingChange = { onSortChange(if (it) SortOption.Holding else SortOption.HoldingReverse) }
            )
        }

        HomeListLabel(
            text = "Price",
            isDownSorting = sortOption == SortOption.Price,
            showSorting = sortOption == SortOption.Price || sortOption == SortOption.PriceReverse,
            onSortingChange = { onSortChange(if (it) SortOption.Price else SortOption.PriceReverse) }
        )
    }
}

@DayPreview
@Composable
private fun HomeCoinListHeadersPreview() {
    CoinTrackerTheme {
        Surface {
            HomeCoinListHeaders(
                contentState = HomeContentState.LivePrices,
                sortOption = SortOption.Rank,
                onSortChange = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun HomeCoinListHeadersNightPreview() {
    CoinTrackerTheme {
        Surface {
            HomeCoinListHeaders(
                contentState = HomeContentState.Portfolio,
                sortOption = SortOption.HoldingReverse,
                onSortChange = {}
            )
        }
    }
}