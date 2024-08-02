package com.smh.home.domain.usecase

import com.smh.database.PortfolioEntity
import com.smh.home.domain.repository.HomeRepository
import com.smh.network.dispatcher.IoDispatcher
import com.smh.network.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPortfolioUseCase @Inject constructor(
    @IoDispatcher override val dispatcher: CoroutineDispatcher,
    private val homeRepository: HomeRepository
): FlowUseCase<Unit, List<PortfolioEntity>>() {
    override fun doWork(params: Unit): Flow<List<PortfolioEntity>> {
        return homeRepository.getAllPortfolio()
    }
}