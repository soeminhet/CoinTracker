package com.smh.detail.data.mapper

import com.smh.design.utility.ifNullOrEmptyUnknown
import com.smh.design.utility.ifNullZero
import com.smh.detail.data.response.CoinDetailResponse
import com.smh.detail.domain.model.CoinDetailModel

fun CoinDetailResponse.toDomain(): CoinDetailModel {
    return CoinDetailModel(
        id = id,
        symbol = symbol?.uppercase().ifNullOrEmptyUnknown(),
        name = name.ifNullOrEmptyUnknown(),
        image = image?.small.orEmpty(),
        blockTimeInMinutes = blockTimeInMinutes ?: 0,
        hashingAlgorithm = hashingAlgorithm.ifNullOrEmptyUnknown(),
        description = description?.en.orEmpty(),
        websiteUrl = links?.homepage?.firstOrNull().orEmpty(),
        redditUrl = links?.subredditUrl.orEmpty(),
        marketCapRank = marketCapRank.ifNullZero(),
        currentPrice = marketData?.currentPrice?.usd.ifNullZero(),
        marketCap = marketData?.marketCap?.usd.ifNullZero(),
        totalVolume = marketData?.totalVolume?.usd.ifNullZero(),
        high24h = marketData?.high24h?.usd.ifNullZero(),
        low24h = marketData?.low24h?.usd.ifNullZero(),
        priceChange24h = marketData?.priceChange24h.ifNullZero(),
        priceChangePercentage24h = marketData?.priceChangePercentage24h.ifNullZero(),
        marketCapChange24h = marketData?.marketCapChange24h.ifNullZero(),
        marketCapChangePercentage24h = marketData?.marketCapChangePercentage24h.ifNullZero(),
        sparkline = marketData?.sparkline7d?.price.orEmpty(),
        lastUpdated = lastUpdated.orEmpty()
    )
}