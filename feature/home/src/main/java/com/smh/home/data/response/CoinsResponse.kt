package com.smh.home.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinResponse(
    @SerialName("id")
    val id: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Int,
    @SerialName("image")
    val image: String?,
    @SerialName("symbol")
    val symbol: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("current_price")
    val currentPrice: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
)