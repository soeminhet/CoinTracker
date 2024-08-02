package com.smh.detail.domain.datasource

import arrow.core.Either
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.network.DataException

interface DetailRemoteDataSource {
    suspend fun getCoinDetail(coinId: String): Either<DataException, CoinDetailModel>
}