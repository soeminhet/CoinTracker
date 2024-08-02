package com.smh.home.data.repository

import arrow.core.Either
import com.smh.database.PortfolioEntity
import com.smh.home.domain.datasource.HomeLocalDataSource
import com.smh.home.domain.datasource.HomeRemoteDataSource
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.home.domain.repository.HomeRepository
import com.smh.network.DataException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource
): HomeRepository {
    override suspend fun getCoins(): Either<DataException, List<CoinModel>> = homeRemoteDataSource.getCoins()

    override suspend fun getGlobal(): Either<DataException, GlobalModel> = homeRemoteDataSource.getGlobal()

    override fun getAllPortfolio(): Flow<List<PortfolioEntity>> = homeLocalDataSource.getAllPortfolio()

    override suspend fun insertPortfolio(entity: PortfolioEntity) = homeLocalDataSource.insertPortfolio(entity)

    override fun updatePortfolio(entity: PortfolioEntity) = homeLocalDataSource.updatePortfolio(entity)

    override fun deletePortfolio(entity: PortfolioEntity) = homeLocalDataSource.deletePortfolio(entity)
}