package com.smh.home.data.datasource

import com.smh.database.PortfolioDao
import com.smh.database.PortfolioEntity
import com.smh.home.domain.datasource.HomeLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val portfolioDao: PortfolioDao
): HomeLocalDataSource {
    override fun getAllPortfolio(): Flow<List<PortfolioEntity>> = portfolioDao.getAll()

    override suspend fun insertPortfolio(entity: PortfolioEntity) = portfolioDao.insert(entity)

    override fun updatePortfolio(entity: PortfolioEntity) = portfolioDao.update(entity)

    override fun deletePortfolio(entity: PortfolioEntity) = portfolioDao.delete(entity)
}