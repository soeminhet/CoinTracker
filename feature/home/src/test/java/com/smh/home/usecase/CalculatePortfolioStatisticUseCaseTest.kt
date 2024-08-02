package com.smh.home.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.smh.design.utility.formattedWithAbbreviations
import com.smh.home.domain.usecase.CalculatePortfolioStatisticUseCase
import com.smh.home.fake.btc
import com.smh.home.fake.eth
import com.smh.home.helper.roundToTwoDecimals
import org.junit.jupiter.api.Test

internal class CalculatePortfolioStatisticUseCaseTest {
    @Test
    fun `One holding coin portfolio value and percentage change`() {
        val holdBtc = btc.copy(holdingAmount = 10.0)
        val result = CalculatePortfolioStatisticUseCase().invoke(listOf(holdBtc))

        assertThat(result.percentageChange?.roundToTwoDecimals()).isEqualTo(holdBtc.priceChangePercentage)
        assertThat(result.value).isEqualTo(holdBtc.holdingPrice.formattedWithAbbreviations())
    }

    @Test
    fun `Two holding coin portfolio value and percentage change`() {
        val holdBtc = btc.copy(holdingAmount = 10.0)
        val holdEth = eth.copy(holdingAmount = 12.55)
        val result = CalculatePortfolioStatisticUseCase().invoke(listOf(holdBtc, holdEth))

        val expectedPercentageChange = 0.66
        val expectedHoldingPrice = (holdBtc.holdingPrice + holdEth.holdingPrice).formattedWithAbbreviations()

        assertThat(result.percentageChange?.roundToTwoDecimals()).isEqualTo(expectedPercentageChange)
        assertThat(result.value).isEqualTo(expectedHoldingPrice)
    }
}