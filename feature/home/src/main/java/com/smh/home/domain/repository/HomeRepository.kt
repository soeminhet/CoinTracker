package com.smh.home.domain.repository

import arrow.core.Either
import com.smh.database.PortfolioEntity
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.network.DataException
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCoins(): Either<DataException, List<CoinModel>>
    suspend fun getGlobal(): Either<DataException, GlobalModel>
    fun getAllPortfolio(): Flow<List<PortfolioEntity>>
    suspend fun insertPortfolio(entity: PortfolioEntity)
    fun updatePortfolio(entity: PortfolioEntity)
    fun deletePortfolio(entity: PortfolioEntity)
}