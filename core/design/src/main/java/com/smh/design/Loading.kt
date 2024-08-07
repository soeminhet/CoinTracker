package com.smh.design

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.Green
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview

@Composable
fun CoinTrackerLoading() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Loading(
                modifier = Modifier.padding(dimensions.space16)
            )
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary,
    itemCount: Int = 10,
    animationDuration: Int = 1000,
    contentDesc: String = stringResource(id = R.string.loadingDesc)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = itemCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Angle"
    )

    Canvas(
        modifier = modifier
            .size(30.dp)
            .semantics {
                contentDescription = contentDesc
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val itemWidth = size.width * 0.25f
        val itemHeight = size.height / itemCount
        val cornerRadius = itemWidth.coerceAtMost(itemHeight) / 2
        val angleOffset = 360f / itemCount

        for (i in 0 until itemCount) {
            val alpha = 0.2f + 0.2f * ((i + 1) % itemCount)
            val color = tint.copy(alpha = alpha.coerceIn(0f, 1f))

            rotate(degrees = (i + angle.toInt()) * angleOffset) {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(canvasWidth - itemWidth, (canvasHeight - itemHeight) / 2),
                    size = Size(itemWidth, itemHeight),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun LoadingPreview() {
    CoinTrackerTheme {
        Surface {
            Loading()
        }
    }
}

@NightPreview
@Composable
private fun LoadingNightPreview() {
    CoinTrackerTheme {
        Surface {
            Loading()
        }
    }
}

@DayPreview
@Composable
private fun CoinTrackerLoadingPreview() {
    CoinTrackerTheme {
        Surface {
            CoinTrackerLoading()
        }
    }
}

@NightPreview
@Composable
private fun CoinTrackerLoadingNightPreview() {
    CoinTrackerTheme {
        Surface {
            CoinTrackerLoading()
        }
    }
}