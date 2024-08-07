package com.smh.home.usecase

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.home.domain.usecase.GetCoinsUseCase
import com.smh.home.fake.HomeRepositoryFake
import com.smh.home.fake.btc
import com.smh.home.fake.eth
import com.smh.network.usecase.executeEmpty
import com.smh.testing.TestCoroutineExtension
import com.smh.testing.apiException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class GetCoinsUseCaseTest {

    private lateinit var repository: HomeRepositoryFake
    private lateinit var useCase: GetCoinsUseCase

    @BeforeEach
    fun setup() {
        repository = HomeRepositoryFake()
        useCase = GetCoinsUseCase(
            dispatcher = StandardTestDispatcher(),
            homeRepository = repository
        )
    }

    @Test
    fun `GetCoin, return 2 coins`() = runTest {
        val actualResult = useCase.executeEmpty()
        val expectedResult = listOf(btc, eth).right()

        assertThat(actualResult.isRight()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetCoin, return Api Exception`() = runTest {
        repository.homeRemoteDataSource.apiException = apiException

        val actualResult = useCase.executeEmpty()
        val expectedResult = apiException.left()

        assertThat(actualResult.isLeft()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}