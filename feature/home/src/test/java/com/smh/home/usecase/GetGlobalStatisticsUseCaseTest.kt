package com.smh.home.usecase

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.home.domain.usecase.GetGlobalStatisticsUseCase
import com.smh.home.fake.HomeRepositoryFake
import com.smh.home.fake.global
import com.smh.home.helper.TestCoroutineExtension
import com.smh.home.ui.model.StatisticModel
import com.smh.network.usecase.executeEmpty
import com.smh.testing.apiException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class GetGlobalStatisticsUseCaseTest {

    private lateinit var repository: HomeRepositoryFake
    private lateinit var usecase: GetGlobalStatisticsUseCase

    @BeforeEach
    fun setup() {
        repository = HomeRepositoryFake()
        usecase = GetGlobalStatisticsUseCase(
            StandardTestDispatcher(),
            repository
        )
    }

    @Test
    fun `Execute empty and return 3 statistics`() = runTest {
        val result = usecase.executeEmpty()

        val marketCap = StatisticModel(1, "Market Cap", global.marketCap, global.marketCapChangePercentage)
        val volume = StatisticModel(2, "24h Volume", global.volume, null)
        val btcDominance = StatisticModel(3, "BTC Dominance", global.btcDominance, null)
        val expectedResult = listOf(
            marketCap,
            volume,
            btcDominance
        ).right()

        assertThat(result.isRight()).isTrue()
        assertThat(result.orNull()?.size).isEqualTo(3)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `Execute empty and return Api Exception`() = runTest {
        repository.homeRemoteDataSource.apiException = apiException
        val result = usecase.executeEmpty()

        assertThat(result.isLeft()).isTrue()
        assertThat(result).isEqualTo(apiException.left())
    }
}