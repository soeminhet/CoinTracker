package com.smh.detail.fake

import arrow.core.Either
import com.smh.detail.domain.datasource.DetailRemoteDataSource
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.network.DataException
import com.smh.testing.apiException

internal class DetailRemoteDataSourceFake: DetailRemoteDataSource {

    override suspend fun getCoinDetail(coinId: String): Either<DataException, CoinDetailModel> {
        return if (coinId != "bitcoin") {
            Either.Left(apiException)
        } else {
            Either.Right(coinDetailModel)
        }
    }
}