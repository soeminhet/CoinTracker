package com.smh.home.data.mapper

import com.smh.home.data.response.CoinResponse
import com.smh.home.domain.model.CoinModel

fun List<CoinResponse>.toDomain(): List<CoinModel> = map { coin ->
    CoinModel(
        id = coin.id,
        rank = coin.marketCapRank,
        image = coin.image ?: "",
        name = (coin.name ?: "unknown").uppercase(),
        symbol = (coin.symbol ?: "unknown").uppercase(),
        price = coin.currentPrice ?: 0.0,
        priceChangePercentage = coin.priceChangePercentage24h ?: 0.0,
        holdingAmount = null
    )
}