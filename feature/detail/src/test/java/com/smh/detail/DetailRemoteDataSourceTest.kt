package com.smh.detail

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.detail.data.datasource.DetailRemoteDataSourceImpl
import com.smh.detail.data.service.DetailApi
import com.smh.detail.domain.datasource.DetailRemoteDataSource
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.fake.coinDetailModel
import com.smh.network.DataException
import com.smh.network.ERROR_TITLE_GENERAL
import com.smh.network.createJson
import com.smh.network.createRetrofitBuilder
import com.smh.network.createService
import com.smh.testing.notFoundResponseBody
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

internal class DetailRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var detailApi: DetailApi
    private lateinit var detailRemoteDataSource: DetailRemoteDataSource

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        detailApi = createRetrofitBuilder(
            okHttpClient = OkHttpClient(),
            networkJson = createJson()
        )
            .baseUrl(mockWebServer.url("/"))
            .build()
            .createService()
        detailRemoteDataSource = DetailRemoteDataSourceImpl(detailApi = detailApi)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GetCoinDetail, Response error, exception is Api 404 and NotFound`() = runBlocking {
        val responseBody = notFoundResponseBody
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = detailRemoteDataSource.getCoinDetail("eth")
        val expectedResult = DataException.Api(message = "Not Found", title = ERROR_TITLE_GENERAL, errorCode = 404).left()
        assertThat(actualResult.isLeft()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `GetCoinDetail, Response success, transformed data is returned`() = runBlocking {
        val responseBody = File("src/test/resources/CoinDetailResponse.json").readText()
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        val actualResult = detailRemoteDataSource.getCoinDetail("bitcoin")
        val expectedResult = coinDetailModel.right()
        assertThat(actualResult.isRight()).isTrue()
        assertThat(expectedResult).isEqualTo(actualResult)
    }
}