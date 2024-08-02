package com.smh.design.utility

fun Int?.ifNullZero(): Int {
    return this ?: 0
}