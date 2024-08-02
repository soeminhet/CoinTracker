package com.smh.design.utility

import java.text.NumberFormat
import java.util.Locale

fun Double.asCurrencyWith6Decimals(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 6
        minimumFractionDigits = 2
    }
    return formatter.format(this)
}

fun Double.asTwoDecimals(): String {
    return String.format(Locale.getDefault(), "%.2f", this)
}

fun Double.asTwoDecimalsPercent(): String {
    return String.format(Locale.getDefault(), "%.2f%%", this)
}

fun Double.formattedWithAbbreviations(): String {
    val num = kotlin.math.abs(this)
    val sign = if (this < 0) "-" else ""

    return when {
        num >= 1_000_000_000_000 -> {
            val formatted = num / 1_000_000_000_000
            val stringFormatted = formatted.asTwoDecimals()
            "$sign${stringFormatted}Tr"
        }
        num >= 1_000_000_000 -> {
            val formatted = num / 1_000_000_000
            val stringFormatted = formatted.asTwoDecimals()
            "$sign${stringFormatted}Bn"
        }
        num >= 1_000_000 -> {
            val formatted = num / 1_000_000
            val stringFormatted = formatted.asTwoDecimals()
            "$sign${stringFormatted}M"
        }
        num >= 1_000 -> {
            val formatted = num / 1_000
            val stringFormatted = formatted.asTwoDecimals()
            "$sign${stringFormatted}K"
        }
        else -> this.asTwoDecimals()
    }
}

fun Double.asCurrencyWithAbbreviations(): String {
    return "$${this.formattedWithAbbreviations()}"
}

fun Double?.ifNullZero(): Double {
    return this ?: 0.0
}

