package com.smh.home.data.datasource

import arrow.core.Either
import com.smh.home.data.mapper.toDomain
import com.smh.home.data.service.HomeApi
import com.smh.home.domain.datasource.HomeRemoteDataSource
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.network.DataException
import com.smh.network.handleCall
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
): HomeRemoteDataSource {
    override suspend fun getCoins(): Either<DataException, List<CoinModel>> {
        return handleCall(
            apiCall = { homeApi.getCoins() },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun getGlobal(): Either<DataException, GlobalModel> {
        return handleCall(
            apiCall = { homeApi.getGlobal() },
            mapper = { it.toDomain() }
        )
    }
}