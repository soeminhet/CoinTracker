package com.smh.home

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import com.smh.home.data.repository.HomeRepositoryImpl
import com.smh.home.domain.repository.HomeRepository
import com.smh.home.fake.HomeLocalDataSourceFake
import com.smh.home.fake.HomeRemoteDataSourceFake
import com.smh.home.fake.btc
import com.smh.home.fake.btcEntity
import com.smh.home.fake.eth
import com.smh.home.fake.ethEntity
import com.smh.home.fake.global
import com.smh.testing.apiException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HomeRepositoryTest {

    private lateinit var homeRemoteDataSource: HomeRemoteDataSourceFake
    private lateinit var homeLocalDataSource: HomeLocalDataSourceFake
    private lateinit var homeRepository: HomeRepository

    @BeforeEach
    fun setup() {
        homeRemoteDataSource = HomeRemoteDataSourceFake()
        homeLocalDataSource = HomeLocalDataSourceFake()
        homeRepository = HomeRepositoryImpl(homeRemoteDataSource, homeLocalDataSource)
    }

    @Test
    fun `GetCoin, return 2 coins`() = runBlocking {
        val actualResult = homeRepository.getCoins()
        val expectedResult = listOf(btc, eth).right()

        assertThat(actualResult.isRight()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetCoin, return Api Exception`() = runBlocking {
        homeRemoteDataSource.apiException = apiException

        val actualResult = homeRepository.getCoins()
        val expectedResult = apiException.left()

        assertThat(actualResult.isLeft()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetGlobal, return data`() = runBlocking {
        val actualResult = homeRepository.getGlobal()
        val expectedResult = global.right()

        assertThat(actualResult.isRight()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetGlobal, return Api Exception`() = runBlocking {
        homeRemoteDataSource.apiException = apiException

        val actualResult = homeRepository.getGlobal()
        val expectedResult = apiException.left()

        assertThat(actualResult.isLeft()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetAllPortfolio, return Empty first, Add one and return one, Delete one and return empty`() = runTest {
        homeRepository.getAllPortfolio().test {
            val firstEmission = awaitItem()
            assertThat(firstEmission).isEmpty()

            homeRepository.insertPortfolio(btcEntity)
            val secondEmission = awaitItem()
            assertThat(secondEmission).isNotEmpty()
            assertThat(secondEmission).contains(btcEntity)

            homeRepository.deletePortfolio(btcEntity)
            val thirdEmission = awaitItem()
            assertThat(thirdEmission).isEmpty()
        }
    }

    @Test
    fun `GetAllPortfolio, return Empty first, Add one and update, return updated`() = runTest {
        homeRepository.getAllPortfolio().test {
            val firstEmission = awaitItem()
            assertThat(firstEmission).isEmpty()

            homeRepository.insertPortfolio(ethEntity)
            awaitItem()
            homeRepository.updatePortfolio(ethEntity.copy(amount = 100.0))
            val secondEmission = awaitItem()
            assertThat(secondEmission).isNotEmpty()
            assertThat(secondEmission.first()).isNotEqualTo(ethEntity)
            assertThat(secondEmission.first()).isNotEqualTo(btcEntity)
        }
    }
}