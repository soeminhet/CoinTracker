package com.smh.home.domain.model

import com.smh.design.utility.asCurrencyWith6Decimals

data class CoinModel(
    val id: String,
    val rank: Int,
    val image: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val priceChangePercentage: Double,
    val holdingAmount: Double?,
) {
    val isPriceUp: Boolean get() = priceChangePercentage > 0

    val displayPrice: String get() = price.asCurrencyWith6Decimals()

    val holdingPrice: Double get() {
        return if (holdingAmount == null) 0.0
        else price * holdingAmount
    }

    val displayHoldingPrice: String get() {
        return if (holdingAmount == null) ""
        else (price * holdingAmount).asCurrencyWith6Decimals()
    }

    companion object {
        val btc = CoinModel(
            id = "bitcoin",
            rank = 1,
            symbol = "BTC",
            image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png",
            name = "BITCOIN",
            price = 56630.00,
            priceChangePercentage = 1.18,
            holdingAmount = 20.0
        )
    }
}