package com.smh.home.domain.usecase

import arrow.core.Either
import com.smh.home.domain.repository.HomeRepository
import com.smh.home.ui.model.StatisticModel
import com.smh.network.DataException
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGlobalStatisticsUseCase @Inject constructor(
    @IoDispatcher override val dispatcher: CoroutineDispatcher,
    private val homeRepository: HomeRepository
): SuspendingUseCase<Unit, Either<DataException, List<StatisticModel>>>() {
    override suspend fun doWork(params: Unit): Either<DataException, List<StatisticModel>> {
        return homeRepository.getGlobal()
            .map { data ->
                val marketCap = StatisticModel(1, "Market Cap", data.marketCap, data.marketCapChangePercentage)
                val volume = StatisticModel(2, "24h Volume", data.volume, null)
                val btcDominance = StatisticModel(3, "BTC Dominance", data.btcDominance, null)
                listOf(
                    marketCap,
                    volume,
                    btcDominance
                )
            }
    }
}