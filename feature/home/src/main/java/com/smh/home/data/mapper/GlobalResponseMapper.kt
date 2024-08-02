package com.smh.home.data.mapper

import com.smh.design.utility.asTwoDecimalsPercent
import com.smh.design.utility.formattedWithAbbreviations
import com.smh.home.data.response.GlobalResponse
import com.smh.home.domain.model.GlobalModel

fun GlobalResponse.toDomain(): GlobalModel {
    return GlobalModel(
        marketCapChangePercentage = data?.marketCapChangePercentage24hUsd ?: 0.0,
        marketCap = (data?.totalMarketCap?.get("usd") ?: 0.0).formattedWithAbbreviations(),
        btcDominance = (data?.marketCapPercentage?.get("btc") ?: 0.0).asTwoDecimalsPercent(),
        volume = (data?.totalVolume?.get("usd") ?: 0.0).formattedWithAbbreviations()
    )
}