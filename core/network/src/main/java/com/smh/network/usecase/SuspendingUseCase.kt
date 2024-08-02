package com.smh.network.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class FlowUseCase<in P, out R> {
    abstract val dispatcher: CoroutineDispatcher
    protected abstract fun doWork(params: P): Flow<R>
    fun execute(params: P): Flow<R> = doWork(params).flowOn(dispatcher)
}

abstract class SuspendingUseCase<in P, out R> {
    abstract val dispatcher: CoroutineDispatcher
    protected abstract suspend fun doWork(params: P): R
    suspend fun execute(params: P): R = withContext(context = dispatcher) { doWork(params) }
}

suspend fun <R> FlowUseCase<Unit, R>.executeEmpty() = this.execute(Unit)
suspend fun <R> SuspendingUseCase<Unit, R>.executeEmpty() = this.execute(Unit)

