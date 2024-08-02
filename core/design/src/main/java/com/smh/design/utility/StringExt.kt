package com.smh.design.utility

fun String?.ifNullOrEmptyUnknown(): String {
    return this?.ifEmpty { "Unknown" } ?: "Unknown"
}