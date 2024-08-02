package com.smh.home.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smh.design.ImageLoader
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.domain.model.CoinModel

@Composable
fun PortfolioSheetCoinLogo(
    modifier: Modifier = Modifier,
    data: CoinModel,
    isSelected: () -> Boolean,
    onClick: (CoinModel) -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected()) GreenAdaptive else Color.Transparent,
        label = "CoinLogoBorder"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.space4),
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = dimensions.size12)
            )
            .clip(RoundedCornerShape(size = dimensions.size12))
            .clickable { onClick(data) }
            .width(dimensions.size90)
            .padding(
                horizontal = dimensions.space8,
                vertical = dimensions.space16
            )
    ) {
        ImageLoader(
            model = data.image,
            modifier = Modifier.size(dimensions.size40)
        )

        Text(
            text = data.symbol,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )

        Text(
            text = data.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@DayPreview
@Composable
private fun PortfolioSheetCoinLogoPreview() {
    CoinTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PortfolioSheetCoinLogo(
                data = CoinModel.btc,
                isSelected = { true },
                onClick = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun PortfolioSheetCoinLogoNightPreview() {
    CoinTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PortfolioSheetCoinLogo(
                data = CoinModel.btc,
                isSelected = { true },
                onClick = {}
            )
        }
    }
}