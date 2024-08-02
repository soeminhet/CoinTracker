package com.smh.home.fake

import arrow.core.Either
import com.smh.database.PortfolioEntity
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.home.domain.repository.HomeRepository
import com.smh.network.DataException
import kotlinx.coroutines.flow.Flow

internal class HomeRepositoryFake: HomeRepository {

    var homeRemoteDataSource: HomeRemoteDataSourceFake = HomeRemoteDataSourceFake()
    private var homeLocalDataSource: HomeLocalDataSourceFake = HomeLocalDataSourceFake()

    override suspend fun getCoins(): Either<DataException, List<CoinModel>> {
        return homeRemoteDataSource.getCoins()
    }

    override suspend fun getGlobal(): Either<DataException, GlobalModel> {
        return homeRemoteDataSource.getGlobal()
    }

    override fun getAllPortfolio(): Flow<List<PortfolioEntity>> {
        return homeLocalDataSource.getAllPortfolio()
    }

    override suspend fun insertPortfolio(entity: PortfolioEntity) {
        return homeLocalDataSource.insertPortfolio(entity)
    }

    override fun updatePortfolio(entity: PortfolioEntity) {
        return homeLocalDataSource.updatePortfolio(entity)
    }

    override fun deletePortfolio(entity: PortfolioEntity) {
        return homeLocalDataSource.deletePortfolio(entity)
    }
}