package com.smh.detail.domain.usecase

import arrow.core.Either
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.domain.repository.DetailRepository
import com.smh.network.DataException
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    @IoDispatcher override val dispatcher: CoroutineDispatcher,
    private val detailRepository: DetailRepository
): SuspendingUseCase<String, Either<DataException, CoinDetailModel>>() {
    override suspend fun doWork(params: String): Either<DataException, CoinDetailModel> {
        return detailRepository.getCoinDetail(coinId = params)
    }
}