package com.smh.home.data.service

import com.smh.home.data.response.CoinResponse
import com.smh.home.data.response.GlobalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * CoinsList
 * https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=true&price_change_percentage=24h
 *
 * GlobalData
 * https://api.coingecko.com/api/v3/global
 */
interface HomeApi {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("price_change_percentage") priceChangePercentage: String = "24h",
        @Query("sparkline") sparkline: Boolean = true,
        @Query("per_page") perPage: Int = 250,
        @Query("page") page: Int = 1,
    ): Response<List<CoinResponse>>

    @GET("global")
    suspend fun getGlobal(): Response<GlobalResponse>
}