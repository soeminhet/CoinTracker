package com.smh.detail.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.RedAdaptive
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.utility.asCurrencyWithAbbreviations
import com.smh.detail.domain.model.CoinDetailModel
import kotlinx.coroutines.delay

@Composable
fun SparkLine7dChartView(
    modifier: Modifier = Modifier,
    data: List<Double>,
    startDate: String,
    endDate: String
) {
    var clipPercentage by remember { mutableFloatStateOf(0f) }
    val color by animateColorAsState(
        targetValue = if ((data.firstOrNull() ?: 0.0) > (data.lastOrNull() ?: 0.0)) RedAdaptive else GreenAdaptive,
        label = "CharColor"
    )
    val minY = remember(data) { data.minOrNull() ?: 0.0 }
    val maxY = remember(data) { data.maxOrNull() ?: 0.0 }
    val midY = maxY - minY
    val yAxis = remember(data) { maxY - minY }

    LaunchedEffect(Unit) {
        while(clipPercentage <= 1) {
            delay(30)
            clipPercentage += 0.01f
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val path = Path().apply {
                    data.forEachIndexed { index, value ->
                        val xPosition = (size.width / data.size.toFloat()) * (index + 1)
                        val yPosition = (1 - (value - minY) / yAxis).toFloat() * size.height
                        if (index == 0) {
                            moveTo(xPosition, yPosition)
                        } else {
                            lineTo(xPosition, yPosition)
                        }
                    }
                }

                clipRect(
                    right = size.width * clipPercentage
                ) {
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
                    Text(
                        text = maxY.asCurrencyWithAbbreviations(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
                    Text(
                        text = midY.asCurrencyWithAbbreviations(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    Text(
                        text = minY.asCurrencyWithAbbreviations(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = startDate,
                style = MaterialTheme.typography.bodySmall,
            )

            Text(
                text = endDate,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@NightPreview
@DayPreview
@Composable
private fun SparkLine7dChartViewPreview() {
    CoinTrackerTheme {
        Surface {
            SparkLine7dChartView(
                data = CoinDetailModel.btc.sparkline,
                startDate = "05/06/24",
                endDate = "12/06/24"
            )
        }
    }
}