package com.smh.home.data.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalResponse(
    @SerialName("data")
    val data: MarketDataResponse?
)

@Serializable
data class MarketDataResponse(
    @SerialName("market_cap_change_percentage_24h_usd")
    val marketCapChangePercentage24hUsd: Double?,
    @SerialName("market_cap_percentage")
    val marketCapPercentage: Map<String, Double>?,
    @SerialName("total_market_cap")
    val totalMarketCap: Map<String, Double>?,
    @SerialName("total_volume")
    val totalVolume: Map<String, Double>?,
)