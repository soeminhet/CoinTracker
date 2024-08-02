package com.smh.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * usecase
 * val dimensions = LocalDimensions.current
 * dimensions.default
 * */

data class Dimensions(
    val default: Dp = 0.dp,

    // Space
    val space2: Dp = 2.dp,
    val space4: Dp = 4.dp,
    val space6: Dp = 6.dp,
    val space8: Dp = 8.dp,
    val space10: Dp = 10.dp,
    val space12: Dp = 12.dp,
    val space16: Dp = 16.dp,
    val space20: Dp = 20.dp,
    val space24: Dp = 24.dp,
    val space32: Dp = 32.dp,

    // Size
    val size8: Dp = 8.dp,
    val size12: Dp = 12.dp,
    val size14: Dp = 14.dp,
    val size22: Dp = 22.dp,
    val size24: Dp = 24.dp,
    val size26: Dp = 26.dp,
    val size30: Dp = 30.dp,
    val size36: Dp = 36.dp,
    val size40: Dp = 40.dp,
    val size42: Dp = 42.dp,
    val size44: Dp = 44.dp,
    val size48: Dp = 48.dp,
    val size50: Dp = 50.dp,
    val size56: Dp = 56.dp,
    val size60: Dp = 60.dp,
    val size64: Dp = 64.dp,
    val size66: Dp = 66.dp,
    val size70: Dp = 70.dp,
    val size76: Dp = 76.dp,
    val size82: Dp = 82.dp,
    val size90: Dp = 90.dp,
    val size100: Dp = 100.dp,
    val size110: Dp = 110.dp,
    val size120: Dp = 120.dp,
    val size140: Dp = 140.dp,

    // Generic
    val horizontalSpace: Dp = space16
)

val LocalDimensions = compositionLocalOf { Dimensions() }

val dimensions @Composable get() = LocalDimensions.current
