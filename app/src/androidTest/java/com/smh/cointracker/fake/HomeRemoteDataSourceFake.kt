package com.smh.cointracker.fake

import arrow.core.Either
import com.smh.home.domain.datasource.HomeRemoteDataSource
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.network.DataException

class HomeRemoteDataSourceFake : HomeRemoteDataSource {

    var apiException: DataException.Api? = null
    val coins = listOf(btc, eth)

    override suspend fun getCoins(): Either<DataException, List<CoinModel>> {
        return if (apiException != null) {
            Either.Left(apiException!!)
        } else {
            Either.Right(coins)
        }
    }

    override suspend fun getGlobal(): Either<DataException, GlobalModel> {
        return if (apiException != null) {
            Either.Left(apiException!!)
        } else {
            Either.Right(
                global
            )
        }
    }
}
