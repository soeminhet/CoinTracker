package com.smh.detail.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.smh.design.StatisticItem
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview

@Composable
fun CoinDetailStatistics(
    modifier: Modifier = Modifier,
    leadingStatLabel: String,
    leadingStatValue: String,
    leadingStatPercentageChange: Double?,
    trailingStatLabel: String,
    trailingStatValue: String,
    trailingStatPercentageChange: Double?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.horizontalSpace)
            .padding(top = dimensions.space24),
    ) {
        StatisticItem(
            title = leadingStatLabel,
            value = leadingStatValue,
            percentageChange = leadingStatPercentageChange,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        )

        StatisticItem(
            title = trailingStatLabel,
            value = trailingStatValue,
            percentageChange = trailingStatPercentageChange,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        )
    }
}

@DayPreview
@Composable
private fun CoinDetailStatisticsPreview() {
    CoinTrackerTheme {
        Surface {
            CoinDetailStatistics(
                leadingStatLabel = "24h Volume",
                leadingStatValue = "$123,456,789",
                leadingStatPercentageChange = 2.3,
                trailingStatLabel = "Market Cap",
                trailingStatValue = "$123,456,789",
                trailingStatPercentageChange = 2.3
            )
        }
    }
}

@NightPreview
@Composable
private fun CoinDetailStatisticsNightPreview() {
    CoinTrackerTheme {
        Surface {
            CoinDetailStatistics(
                leadingStatLabel = "24h Volume",
                leadingStatValue = "$123,456,789",
                leadingStatPercentageChange = 2.3,
                trailingStatLabel = "Market Cap",
                trailingStatValue = "$123,456,789",
                trailingStatPercentageChange = 2.3
            )
        }
    }
}