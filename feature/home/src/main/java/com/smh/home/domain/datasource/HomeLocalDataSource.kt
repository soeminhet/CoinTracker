package com.smh.home.domain.datasource

import com.smh.database.PortfolioEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun getAllPortfolio(): Flow<List<PortfolioEntity>>
    suspend fun insertPortfolio(entity: PortfolioEntity)
    fun updatePortfolio(entity: PortfolioEntity)
    fun deletePortfolio(entity: PortfolioEntity)
}