package com.smh.home.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.smh.design.utility.CLAnimationFast
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.StatisticItem
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.home.ui.HomeContentState
import com.smh.home.ui.model.StatisticModel

@Composable
fun HomeStatistics(
    modifier: Modifier = Modifier,
    contentState: HomeContentState,
    statistics: List<StatisticModel>
) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp / 3
    val screenWidthPx = with(density) { screenWidthDp.toPx() }

    LaunchedEffect(contentState) {
        if (contentState == HomeContentState.LivePrices && scrollState.canScrollBackward) {
            scrollState.animateScrollBy(
                -screenWidthPx,
                animationSpec = tween(durationMillis = CLAnimationFast)
            )
        } else if (contentState == HomeContentState.Portfolio && scrollState.canScrollForward) {
            scrollState.animateScrollBy(
                screenWidthPx,
                animationSpec = tween(durationMillis = CLAnimationFast)
            )
        }
    }

    Row(
        modifier = modifier
            .padding(top = dimensions.space20)
            .horizontalScroll(
                state = scrollState,
                enabled = false
            )
            .fillMaxWidth()
    ) {
        statistics.forEach { stat ->
            StatisticItem(
                modifier = Modifier.width(screenWidthDp),
                title = stat.title,
                value = stat.value,
                percentageChange = stat.percentageChange
            )
        }
    }
}

@DayPreview
@Composable
private fun HomeStatisticsPreview() {
    CoinTrackerTheme {
        Surface {
            HomeStatistics(
                contentState = HomeContentState.LivePrices,
                statistics = StatisticModel.statistics
            )
        }
    }
}

@NightPreview
@Composable
private fun HomeStatisticNightPreview() {
    CoinTrackerTheme {
        Surface {
            HomeStatistics(
                contentState = HomeContentState.Portfolio,
                statistics = StatisticModel.statistics
            )
        }
    }
}