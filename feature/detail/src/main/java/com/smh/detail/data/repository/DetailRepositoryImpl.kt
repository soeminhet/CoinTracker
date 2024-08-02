package com.smh.detail.data.repository

import arrow.core.Either
import com.smh.detail.domain.datasource.DetailRemoteDataSource
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.domain.repository.DetailRepository
import com.smh.network.DataException
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource
): DetailRepository {
    override suspend fun getCoinDetail(coinId: String): Either<DataException, CoinDetailModel> = detailRemoteDataSource.getCoinDetail(coinId)
}