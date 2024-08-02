package com.smh.home.fake

import com.smh.database.PortfolioEntity
import com.smh.home.domain.datasource.HomeLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeLocalDataSourceFake : HomeLocalDataSource {
    private val portfolioData = MutableStateFlow<List<PortfolioEntity>>(emptyList())

    override fun getAllPortfolio(): Flow<List<PortfolioEntity>> {
        return portfolioData
    }

    override suspend fun insertPortfolio(entity: PortfolioEntity) {
        val currentData = portfolioData.value.toMutableList()
        currentData.add(entity)
        portfolioData.value = currentData
    }

    override fun updatePortfolio(entity: PortfolioEntity) {
        val currentData = portfolioData.value.toMutableList()
        val index = currentData.indexOfFirst { it.coinId == entity.coinId }
        if (index != -1) {
            currentData[index] = entity
            portfolioData.value = currentData
        }
    }

    override fun deletePortfolio(entity: PortfolioEntity) {
        val currentData = portfolioData.value.toMutableList()
        currentData.removeIf { it.coinId == entity.coinId }
        portfolioData.value = currentData
    }
}
