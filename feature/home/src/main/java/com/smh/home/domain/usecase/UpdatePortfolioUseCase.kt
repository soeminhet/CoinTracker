package com.smh.home.domain.usecase

import com.smh.database.PortfolioEntity
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.repository.HomeRepository
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdatePortfolioUseCase @Inject constructor(
    @IoDispatcher override val dispatcher: CoroutineDispatcher,
    private val homeRepository: HomeRepository
): SuspendingUseCase<Pair<CoinModel, Double>, Unit>() {
    override suspend fun doWork(params: Pair<CoinModel, Double>) {
        val (coin, amount) = params
        val entity = PortfolioEntity(coin.id, amount)
        if (coin.holdingAmount == null) {
            if (amount > 0) {
                homeRepository.insertPortfolio(entity)
            }
        } else {
            if (amount > 0) {
                homeRepository.updatePortfolio(entity)
            } else {
                homeRepository.deletePortfolio(entity)
            }
        }
    }
}