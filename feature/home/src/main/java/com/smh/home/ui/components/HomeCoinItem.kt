package com.smh.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.RedAdaptive
import com.smh.design.theme.dimensions
import com.smh.design.ImageLoader
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.domain.model.CoinModel

@Composable
fun HomeCoinItem(
    modifier: Modifier = Modifier,
    data: CoinModel,
    showHolding: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.rank.toString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(dimensions.size26),
                textAlign = TextAlign.Start
            )

            ImageLoader(
                model = data.image,
                modifier = Modifier
                    .padding(end = dimensions.space8)
                    .size(dimensions.size30),
            )

            Text(
                text = data.symbol.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(Modifier.weight(1f))

        if (showHolding) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = dimensions.space8)
            ) {
                Text(
                    text = data.displayHoldingPrice,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W500
                )

                Text(
                    text = "${data.holdingAmount}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(start = dimensions.space8)
        ) {
            Text(
                text = data.displayPrice,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = "${data.priceChangePercentage}%",
                color = if (data.isPriceUp) GreenAdaptive else RedAdaptive,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "Detail",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

private class ShowHoldingProvider: PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}

@DayPreview
@Composable
private fun CoinItemPreview(
    @PreviewParameter(ShowHoldingProvider::class) showHolding: Boolean
) {
    CoinTrackerTheme {
        Surface {
            HomeCoinItem(
                data = CoinModel.btc,
                showHolding = showHolding
            )
        }
    }
}

@NightPreview
@Composable
private fun CoinItemDarkPreview(
    @PreviewParameter(ShowHoldingProvider::class) showHolding: Boolean
) {
    CoinTrackerTheme {
        Surface {
            HomeCoinItem(
                data = CoinModel.btc,
                showHolding = showHolding
            )
        }
    }
}