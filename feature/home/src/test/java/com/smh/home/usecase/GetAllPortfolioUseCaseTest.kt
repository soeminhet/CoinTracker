package com.smh.home.usecase

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import com.smh.home.domain.usecase.GetAllPortfolioUseCase
import com.smh.home.fake.HomeRepositoryFake
import com.smh.home.fake.btcEntity
import com.smh.home.fake.ethEntity
import com.smh.home.helper.TestCoroutineExtension
import com.smh.network.usecase.executeEmpty
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class GetAllPortfolioUseCaseTest {

    private lateinit var repository: HomeRepositoryFake
    private lateinit var useCase: GetAllPortfolioUseCase

    @BeforeEach
    fun setup() {
        repository = HomeRepositoryFake()
        useCase = GetAllPortfolioUseCase(
            dispatcher = StandardTestDispatcher(),
            homeRepository = repository
        )
    }

    @Test
    fun `Collect, Insert, Update, Delete`() = runTest {
        useCase.executeEmpty().test {
            val firstEmission = awaitItem()
            assertThat(firstEmission).isEmpty()

            repository.insertPortfolio(btcEntity)
            val secondEmission = awaitItem()
            assertThat(secondEmission.size).isEqualTo(1)
            assertThat(secondEmission).contains(btcEntity)

            repository.updatePortfolio(btcEntity.copy(amount = 100.0))
            val thirdEmission = awaitItem()
            assertThat(thirdEmission.size).isEqualTo(1)
            assertThat(thirdEmission.first().amount).isEqualTo(100.0)

            repository.insertPortfolio(ethEntity)
            val fourthEmission = awaitItem()
            assertThat(fourthEmission.size).isGreaterThan(1)
            assertThat(fourthEmission).contains(ethEntity)

            repository.deletePortfolio(btcEntity)
            val fifthEmission = awaitItem()
            assertThat(fifthEmission.size).isLessThan(2)
            assertThat(fourthEmission).doesNotContain(btcEntity)
        }
    }
}