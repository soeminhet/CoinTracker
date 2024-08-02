package com.smh.design.utility

fun Long?.ifNullZero(): Long {
    return this ?: 0
}