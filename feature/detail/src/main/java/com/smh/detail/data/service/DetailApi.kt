package com.smh.detail.data.service

import com.smh.detail.data.response.CoinDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * https://api.coingecko.com/api/v3/coins/bitcoin?
 * localization=false&tickers=false&community_data=false&developer_data=false&sparkline=true
 */
interface DetailApi {
    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = true,
    ): Response<CoinDetailResponse>
}