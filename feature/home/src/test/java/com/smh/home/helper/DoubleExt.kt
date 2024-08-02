package com.smh.home.helper

internal fun Double.roundToTwoDecimals(): Double {
    return kotlin.math.round(this * 100) / 100.0
}