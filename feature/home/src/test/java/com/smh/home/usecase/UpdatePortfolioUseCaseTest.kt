package com.smh.home.usecase

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.smh.home.domain.usecase.UpdatePortfolioUseCase
import com.smh.home.fake.HomeRepositoryFake
import com.smh.home.fake.btc
import com.smh.home.helper.TestCoroutineExtension
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class UpdatePortfolioUseCaseTest {

    private lateinit var repository: HomeRepositoryFake
    private lateinit var usecase: UpdatePortfolioUseCase

    @BeforeEach
    fun setup() {
        repository = HomeRepositoryFake()
        usecase = UpdatePortfolioUseCase(
            StandardTestDispatcher(),
            repository
        )
    }

    @Test
    fun `Holding amount is null and amount is 0 ,and Holding amount is null and amount is greater than 0`() = runTest {
        repository.getAllPortfolio().test {
            awaitItem() // Skip initial value

            usecase.execute(params = btc to 0.0)
            usecase.execute(params = btc to 0.1)
            val emission1 = awaitItem()
            assertThat(emission1.size).isEqualTo(1)
        }
    }

    @Test
    fun `Holding amount is not null and amount is 0`() = runTest {
        repository.getAllPortfolio().test {
            awaitItem() // Skip initial value

            usecase.execute(params = btc to 1.0)
            awaitItem()

            usecase.execute(params = btc.copy(holdingAmount = 1.0) to 0.0)
            val emission1 = awaitItem()
            assertThat(emission1).isEmpty()
        }
    }

    @Test
    fun `Holding amount is not null and amount change`() = runTest {
        repository.getAllPortfolio().test {
            awaitItem() // Skip initial value

            usecase.execute(params = btc to 1.0)
            awaitItem()

            usecase.execute(params = btc.copy(holdingAmount = 1.0) to 12.20)
            val emission1 = awaitItem()
            assertThat(emission1.first().amount).isEqualTo(12.20)
        }
    }

}