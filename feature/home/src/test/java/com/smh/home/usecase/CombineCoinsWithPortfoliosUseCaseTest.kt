package com.smh.home.usecase

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.smh.home.domain.usecase.CombineCoinsWithPortfoliosUseCase
import com.smh.home.fake.btc
import com.smh.home.fake.btcEntity
import com.smh.home.fake.eth
import com.smh.home.fake.ethEntity
import org.junit.jupiter.api.Test

internal class CombineCoinsWithPortfoliosUseCaseTest {

    @Test
    fun `Empty list add and return empty list`() {
        val actualResult = CombineCoinsWithPortfoliosUseCase().invoke(
            coins = emptyList(),
            portfolios = emptyList()
        )

        assertThat(actualResult).isEmpty()
    }

    @Test
    fun `Empty coin list add and return empty list`() {
        val actualResult = CombineCoinsWithPortfoliosUseCase().invoke(
            coins = emptyList(),
            portfolios = listOf(btcEntity)
        )

        assertThat(actualResult).isEmpty()
    }

    @Test
    fun `Empty portfolio list add and return empty list`() {
        val actualResult = CombineCoinsWithPortfoliosUseCase().invoke(
            coins = listOf(btc),
            portfolios = emptyList()
        )

        assertThat(actualResult).isEmpty()
    }

    @Test
    fun `Two coins and two portfolio, return combined list`() {
        val actualResult = CombineCoinsWithPortfoliosUseCase().invoke(
            coins = listOf(btc, eth),
            portfolios = listOf(btcEntity, ethEntity)
        )
        val expectedResult = listOf(
            btc.copy(holdingAmount = btcEntity.amount),
            eth.copy(holdingAmount = ethEntity.amount)
        )

        assertThat(actualResult.size).isEqualTo(2)
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}