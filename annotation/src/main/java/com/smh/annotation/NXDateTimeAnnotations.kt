package com.smh.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NXDateTimeExtension

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class NXStringToString(
    val originPattern: String,
    val targetPattern: String,
    val prefixName: String = "nx_"
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class NXLongToString(
    val targetPattern: String,
    val prefixName: String = "nx_"
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class NXDateToString(
    val targetPattern: String,
    val prefixName: String = "nx_"
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class NXStringToDate(
    val originPattern: String,
    val prefixName: String = "nx_"
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class NXLongToDate(
    val prefixName: String = "nx_"
)



