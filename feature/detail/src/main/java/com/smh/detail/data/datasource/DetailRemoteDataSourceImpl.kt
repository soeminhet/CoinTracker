package com.smh.detail.data.datasource

import arrow.core.Either
import com.smh.detail.data.mapper.toDomain
import com.smh.detail.data.service.DetailApi
import com.smh.detail.domain.datasource.DetailRemoteDataSource
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.network.DataException
import com.smh.network.handleCall
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi
): DetailRemoteDataSource {
    override suspend fun getCoinDetail(coinId: String): Either<DataException, CoinDetailModel> {
        return handleCall(
            apiCall = { detailApi.getCoinDetail(coinId) },
            mapper = { it.toDomain() }
        )
    }
}