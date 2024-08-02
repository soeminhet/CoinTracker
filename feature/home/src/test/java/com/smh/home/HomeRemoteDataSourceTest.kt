package com.smh.home

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.home.data.datasource.HomeRemoteDataSourceImpl
import com.smh.home.data.service.HomeApi
import com.smh.home.domain.datasource.HomeRemoteDataSource
import com.smh.home.domain.model.CoinModel
import com.smh.home.domain.model.GlobalModel
import com.smh.network.DataException
import com.smh.network.ERROR_TITLE_GENERAL
import com.smh.network.createJson
import com.smh.network.createRetrofitBuilder
import com.smh.network.createService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

internal class HomeRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var homeApi: HomeApi
    private lateinit var homeRemoteDataSource: HomeRemoteDataSource

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        homeApi = createRetrofitBuilder(
            okHttpClient = OkHttpClient(),
            networkJson = createJson()
        )
            .baseUrl(mockWebServer.url("/"))
            .build()
            .createService()
        homeRemoteDataSource = HomeRemoteDataSourceImpl(homeApi = homeApi)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GetCoin, Response error, exception is Api 404 and NotFound`() = runBlocking {
        val responseBody = File("src/test/resources/NotFoundResponse.json").readText()
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = homeRemoteDataSource.getCoins()
        val expectedResult = DataException.Api(message = "Not Found", title = ERROR_TITLE_GENERAL, errorCode = 404).left()
        assertThat(actualResult.isLeft()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `GetCoin, Response success, data is 2 coin`() = runBlocking {
        val responseBody = File("src/test/resources/CoinResponse.json").readText()
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = homeRemoteDataSource.getCoins()
        val coins = listOf(
            CoinModel(
                id = "bitcoin",
                rank = 1,
                image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                symbol = "BTC",
                name = "BITCOIN",
                price = 66199.0,
                priceChangePercentage = -0.84324,
                holdingAmount = null
            ),
            CoinModel(
                id = "ethereum",
                rank = 2,
                image = "https://coin-images.coingecko.com/coins/images/279/large/ethereum.png?1696501628",
                symbol = "ETH",
                name = "ETHEREUM",
                price = 3319.38,
                priceChangePercentage = -1.14169,
                holdingAmount = null
            )
        )
        val expectedResult = coins.right()
        assertThat(actualResult.isRight()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `GetGlobal, Response error, exception is Api 404 and NotFound`() = runBlocking {
        val responseBody = File("src/test/resources/NotFoundResponse.json").readText()
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = homeRemoteDataSource.getGlobal()
        val expectedResult = DataException.Api(message = "Not Found", title = ERROR_TITLE_GENERAL, errorCode = 404).left()
        assertThat(actualResult.isLeft()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `GetCoin, Response success, transformed data is returned`() = runBlocking {
        val responseBody = File("src/test/resources/GlobalResponse.json").readText()
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = homeRemoteDataSource.getGlobal()
        val expectedResult = GlobalModel(
            marketCapChangePercentage = -0.21460460640491,
            marketCap = "2.50Tr",
            btcDominance = "52.34%",
            volume = "77.07Bn"
        ).right()
        assertThat(actualResult.isRight()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }
}