package com.smh.annotation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.changeFormatDate(
    originPattern: String,
    targetPattern: String,
    default: String = "-"
): String {
    if (this.isNullOrEmpty()) return default
    return try {
        val fromFormat = SimpleDateFormat(originPattern, Locale.getDefault())
        val toFormat = SimpleDateFormat(targetPattern, Locale.getDefault())
        val date = fromFormat.parse(this)
        if (date != null) {
            toFormat.format(date)
        } else {
            default
        }
    } catch (e: Exception) {
        default
    }
}

fun Long?.changeFormatDate(
    targetPattern: String,
    default: String = "-"
): String {
    if (this == null) return default
    return try {
        val date = Date(this)
        val toFormat = SimpleDateFormat(targetPattern, Locale.getDefault())
        toFormat.format(date)
    } catch (e: Exception) {
        default
    }
}

fun Date?.changeFormatDate(
    targetPattern: String,
    default: String = "-"
): String {
    if (this == null) return default
    return try {
        val toFormat = SimpleDateFormat(targetPattern, Locale.getDefault())
        toFormat.format(this)
    } catch (e: Exception) {
        default
    }
}

fun String?.toDate(
    originPattern: String
): Date? {
    if (this.isNullOrEmpty()) return null
    return try {
        val fromFormat = SimpleDateFormat(originPattern, Locale.getDefault())
        fromFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Long?.toDate(): Date? {
    if (this == null) return null
    return try {
        Date(this)
    } catch (e: Exception) {
        null
    }
}