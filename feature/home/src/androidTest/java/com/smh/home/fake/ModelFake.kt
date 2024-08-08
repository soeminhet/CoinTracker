package com.smh.home.fake

import com.smh.database.PortfolioEntity
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.home.ui.model.StatisticModel

internal val btc = CoinModel(
    id = "bitcoin",
    rank = 1,
    symbol = "BTC",
    image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png",
    name = "BITCOIN",
    price = 56630.00,
    priceChangePercentage = 1.18,
    holdingAmount = null
)

internal val eth = CoinModel(
    id = "ethereum",
    rank = 2,
    symbol = "ETH",
    image = "https://coin-images.coingecko.com/coins/images/1/large/ethereum.png",
    name = "ETHEREUM",
    price = 12333.00,
    priceChangePercentage = -1.18,
    holdingAmount = null
)

internal val usdt = CoinModel(
    id = "usdt",
    rank = 3,
    symbol = "USDT",
    image = "https://coin-images.coingecko.com/coins/images/1/large/usdt.png",
    name = "USDT",
    price = 0.99,
    priceChangePercentage = 1.90,
    holdingAmount = null
)

internal val global = GlobalModel(
    marketCapChangePercentage = 0.55,
    marketCap = "1.2TR",
    btcDominance = "40%",
    volume = "200Bn"
)

internal val marketCapStat = StatisticModel(
    id = 1,
    title = "Market Cap",
    value = "$2.50Tr",
    percentageChange = 3.72
)

internal val volumeStat = StatisticModel(
    id = 2,
    title = "24h Volume",
    value = "$103.15Bn",
    percentageChange = null
)

internal val btcDominanceStat = StatisticModel(
    id = 3,
    title = "BTC Dominance",
    value = "51.21%",
    percentageChange = null
)

internal val portfolioValueStat = StatisticModel(
    id = 4,
    title = "Portfolio Value",
    value = "$3.50Tr",
    percentageChange = 3.28
)