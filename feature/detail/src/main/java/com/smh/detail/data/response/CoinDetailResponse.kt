package com.smh.detail.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailResponse(
    @SerialName("id")
    val id: String,
    @SerialName("symbol")
    val symbol: String?,
    @SerialName("image")
    val image: CoinDetailImageResponse?,
    @SerialName("name")
    val name: String?,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String?,
    @SerialName("description")
    val description: CoinDetailDescriptionResponse?,
    @SerialName("links")
    val links: CoinDetailLinksResponse?,
    @SerialName("market_cap_rank")
    val marketCapRank: Int?,
    @SerialName("market_data")
    val marketData: CoinDetailMarketDataResponse?,
)

@Serializable
data class CoinDetailImageResponse(
    @SerialName("small")
    val small: String
)

@Serializable
data class CoinDetailDescriptionResponse(
    @SerialName("en")
    val en: String?
)

@Serializable
data class CoinDetailLinksResponse(
    @SerialName("homepage")
    val homepage: List<String>,
    @SerialName("subreddit_url")
    val subredditUrl: String?,
)

@Serializable
data class CoinDetailMarketDataResponse(
    @SerialName("current_price")
    val currentPrice: MarketUsdResponse?,
    @SerialName("market_cap")
    val marketCap: MarketUsdResponse?,
    @SerialName("total_volume")
    val totalVolume: MarketUsdResponse?,
    @SerialName("high_24h")
    val high24h: MarketUsdResponse?,
    @SerialName("low_24h")
    val low24h: MarketUsdResponse?,
    @SerialName("price_change_24h")
    val priceChange24h: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @SerialName("market_cap_change_24h")
    val marketCapChange24h: Double?,
    @SerialName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double?,
    @SerialName("sparkline_7d")
    val sparkline7d: SparkLine7dResponse?,
    @SerialName("last_updated")
    val lastUpdated: String?,
)

@Serializable
data class SparkLine7dResponse(
    @SerialName("price")
    val price: List<Double>?,
)

@Serializable
data class MarketUsdResponse(
    @SerialName("usd")
    val usd: Double?,
)