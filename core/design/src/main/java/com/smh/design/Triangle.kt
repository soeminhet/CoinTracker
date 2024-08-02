package com.smh.design

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun Triangle(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Canvas(modifier = modifier) {
        drawTriangle(
            width = size.width,
            height = size.height,
            color = color
        )
    }
}

private fun DrawScope.drawTriangle(
    width: Float,
    height: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(width / 2, 0f)
        lineTo(0f, height)
        lineTo(width, height)
        close()
    }
    drawPath(
        path = path,
        color = color,
        style = Fill
    )
}