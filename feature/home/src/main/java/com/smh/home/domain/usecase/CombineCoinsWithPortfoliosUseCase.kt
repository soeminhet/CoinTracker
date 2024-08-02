package com.smh.home.domain.usecase

import com.smh.database.PortfolioEntity
import com.smh.home.domain.model.CoinModel
import javax.inject.Inject

class CombineCoinsWithPortfoliosUseCase @Inject constructor() {
    operator fun invoke(coins: List<CoinModel>, portfolios: List<PortfolioEntity>): List<CoinModel> {
        val portfolioMap = portfolios.associateBy { it.coinId }
        return coins.mapNotNull { coin ->
            portfolioMap[coin.id]?.let { matchedCoin ->
                coin.copy(holdingAmount = matchedCoin.amount)
            }
        }
    }
}