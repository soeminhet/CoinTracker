package com.smh.detail.domain.repository

import arrow.core.Either
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.network.DataException

interface DetailRepository {
    suspend fun getCoinDetail(coinId: String): Either<DataException, CoinDetailModel>
}