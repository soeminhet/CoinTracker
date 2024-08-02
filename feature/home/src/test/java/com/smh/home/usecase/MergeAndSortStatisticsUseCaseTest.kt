package com.smh.home.usecase

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.smh.home.domain.usecase.MergeAndSortStatisticsUseCase
import com.smh.home.fake.btcDominanceStat
import com.smh.home.fake.marketCapStat
import com.smh.home.fake.portfolioValueStat
import com.smh.home.fake.volumeStat
import org.junit.jupiter.api.Test

internal class MergeAndSortStatisticsUseCaseTest {

    @Test
    fun `Merge and Sort Statistics with empty list`() {
        val actualResult = MergeAndSortStatisticsUseCase().invoke(
            oldStatistics = listOf(),
            newStatistics = listOf()
        )
        assertThat(actualResult).isEmpty()
    }

    @Test
    fun `Merge and Sort Statistics with non-empty list`() {
        val actualResult = MergeAndSortStatisticsUseCase().invoke(
            oldStatistics = listOf(marketCapStat, volumeStat, btcDominanceStat),
            newStatistics = listOf(portfolioValueStat)
        )

        assertThat(actualResult.size).isEqualTo(4)
        assertThat(actualResult.map { it.id }).isEqualTo(listOf(1, 2, 3, 4))
    }
}