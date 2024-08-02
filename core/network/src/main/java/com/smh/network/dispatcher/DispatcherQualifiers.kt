package com.smh.network.dispatcher

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ComputationDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UIDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AppCoroutineScope