package com.smh.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.RedAdaptive
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.utility.asTwoDecimalsPercent

@Composable
fun StatisticItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    percentageChange: Double?,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(dimensions.space4)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        if (percentageChange != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Triangle(
                    color = if (percentageChange > 0) GreenAdaptive else RedAdaptive,
                    modifier = Modifier
                        .size(10.dp)
                        .graphicsLayer {
                            rotationZ = if (percentageChange > 0) 0f else 180f
                        }
                )

                Text(
                    text = percentageChange.asTwoDecimalsPercent(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = dimensions.space4),
                    color = if (percentageChange > 0) GreenAdaptive else RedAdaptive
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun StatisticItemPreview() {
    CoinTrackerTheme {
        Surface {
            StatisticItem(
                title = "Market Cap",
                value = "$12.50Bn",
                percentageChange = 15.666666
            )
        }
    }
}

@NightPreview
@Composable
private fun StatisticItemNightPreview() {
    CoinTrackerTheme {
        Surface {
            StatisticItem(
                title = "Market Cap",
                value = "$12.50Bn",
                percentageChange = -15.666666
            )
        }
    }
}