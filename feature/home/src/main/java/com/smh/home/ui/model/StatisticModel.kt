package com.smh.home.ui.model

data class StatisticModel(
    val id: Int,
    val title: String,
    val value: String,
    val percentageChange: Double?
) {
    companion object {
        val marketCap = StatisticModel(
            id = 1,
            title = "Market Cap",
            value = "$2.50Tr",
            percentageChange = 3.72
        )

        val volume = StatisticModel(
            id = 1,
            title = "24h Volume",
            value = "$103.15Bn",
            percentageChange = null
        )

        val btcDominance = StatisticModel(
            id = 1,
            title = "BTC Dominance",
            value = "51.21%",
            percentageChange = null
        )

        val portfolioValue = StatisticModel(
            id = 1,
            title = "Portfolio Value",
            value = "$3.50Tr",
            percentageChange = 3.28
        )

        val statistics = listOf(marketCap, volume, btcDominance, portfolioValue)
    }
}
