package com.smh.home.ui.components

import CircleButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.home.R
import com.smh.home.ui.HomeContentState

@Composable
fun HomeHeader(
    contentState: HomeContentState,
    modifier: Modifier = Modifier,
    onShowLivePricesChange: (HomeContentState) -> Unit,
    onEditPortfolioClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.horizontalSpace)
            .padding(top = dimensions.space20)
    ) {
        HomeInfoAndAddCircleButton(
            contentState = contentState,
            onClick = {
                when(contentState) {
                    HomeContentState.LivePrices -> onInfoClick()
                    HomeContentState.Portfolio -> onEditPortfolioClick()
                }
            }
        )

        Text(
            text = when(contentState) {
                HomeContentState.LivePrices -> stringResource(id = R.string.live_prices)
                HomeContentState.Portfolio -> stringResource(id = R.string.portfolio)
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        HomeToggleShowLivePricesCircleButton(
            contentState = contentState,
            onClick = { onShowLivePricesChange(contentState.toggle()) }
        )
    }
}

@Composable
private fun HomeInfoAndAddCircleButton(
    contentState: HomeContentState,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = when(contentState) {
            HomeContentState.LivePrices -> 0.7f
            HomeContentState.Portfolio -> 1.8f
        },
        label = "Scale",
        animationSpec = tween(1000)
    )
    val alpha by animateFloatAsState(
        targetValue = when(contentState) {
            HomeContentState.LivePrices -> 1f
            HomeContentState.Portfolio -> 0f
        },
        label = "Alpha",
        animationSpec = tween(1000)
    )
    val circleColor by animateColorAsState(
        targetValue = when(contentState) {
            HomeContentState.LivePrices -> Color.Transparent
            HomeContentState.Portfolio -> MaterialTheme.colorScheme.inversePrimary
        },
        label = "CircleColor"
    )

    CircleButton(
        icon = when(contentState) {
            HomeContentState.LivePrices -> com.smh.design.R.drawable.ic_info
            HomeContentState.Portfolio -> com.smh.design.R.drawable.ic_add
        },
        onClick = onClick,
        modifier = Modifier.drawBehind {
            scale(scale) {
                drawCircle(
                    color = circleColor,
                    style = Stroke(width = 1.5f),
                    alpha = alpha
                )
            }
        },
        contentDescription = stringResource(id = R.string.infoAndAddCircleDesc)
    )
}

@Composable
private fun HomeToggleShowLivePricesCircleButton(
    contentState: HomeContentState,
    onClick: () -> Unit
) {
    val arrowDegree by animateFloatAsState(
        targetValue = when(contentState) {
            HomeContentState.LivePrices -> 0f
            HomeContentState.Portfolio -> 180f
        },
        label = "ArrowDegree"
    )

    CircleButton(
        icon = com.smh.design.R.drawable.ic_chevron_right,
        onClick = onClick,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = arrowDegree
            },
        contentDescription = stringResource(id = R.string.toggleShowLivePricesDesc)
    )
}

@DayPreview
@Composable
private fun HomeContentPreview() {
    CoinTrackerTheme {
        Surface {
            HomeHeader(
                contentState = HomeContentState.LivePrices,
                onShowLivePricesChange = {},
                onInfoClick = {},
                onEditPortfolioClick = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun HomeContentNightPreview() {
    CoinTrackerTheme {
        Surface {
            HomeHeader(
                contentState = HomeContentState.Portfolio,
                onShowLivePricesChange = {},
                onInfoClick = {},
                onEditPortfolioClick = {}
            )
        }
    }
}