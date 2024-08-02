package com.smh.home.domain.usecase

import arrow.core.Either
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.repository.HomeRepository
import com.smh.network.DataException
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    @IoDispatcher override val dispatcher: CoroutineDispatcher,
    private val homeRepository: HomeRepository
): SuspendingUseCase<Unit, Either<DataException, List<CoinModel>>>() {
    override suspend fun doWork(params: Unit): Either<DataException, List<CoinModel>> {
        return homeRepository.getCoins()
    }
}