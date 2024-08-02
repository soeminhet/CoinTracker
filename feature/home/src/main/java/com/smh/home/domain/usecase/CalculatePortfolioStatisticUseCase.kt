package com.smh.home.domain.usecase

import com.smh.design.utility.formattedWithAbbreviations
import com.smh.home.domain.model.CoinModel
import com.smh.home.ui.model.StatisticModel
import javax.inject.Inject

class CalculatePortfolioStatisticUseCase @Inject constructor() {
    operator fun invoke(portfolios: List<CoinModel>): StatisticModel {
        val currentPortfolioValue = portfolios.fold(0.0) { acc, coin -> acc + coin.holdingPrice }
        val previousPortfolioValue = portfolios.fold(0.0) { acc, coin ->
            val changeMultiplier = 1.0 + (coin.priceChangePercentage / 100.0)
            val previousValue =  coin.holdingPrice / changeMultiplier
            acc + previousValue
        }
        val percentageChange = if (previousPortfolioValue == 0.0) 0.0 else {
            ((currentPortfolioValue - previousPortfolioValue) / previousPortfolioValue) * 100
        }
        return StatisticModel(
            4,
            "Portfolio Value",
            currentPortfolioValue.formattedWithAbbreviations(),
            percentageChange
        )
    }
}