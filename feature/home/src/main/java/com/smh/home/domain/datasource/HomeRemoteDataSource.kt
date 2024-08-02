package com.smh.home.domain.datasource

import arrow.core.Either
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.network.DataException

interface HomeRemoteDataSource {
    suspend fun getCoins(): Either<DataException, List<CoinModel>>
    suspend fun getGlobal(): Either<DataException, GlobalModel>
}