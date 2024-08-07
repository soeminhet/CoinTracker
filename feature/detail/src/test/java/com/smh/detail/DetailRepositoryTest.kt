package com.smh.detail

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.detail.data.repository.DetailRepositoryImpl
import com.smh.detail.domain.model.CoinDetailModel.Companion.btc
import com.smh.detail.domain.repository.DetailRepository
import com.smh.detail.fake.DetailRemoteDataSourceFake
import com.smh.detail.fake.apiException
import com.smh.detail.fake.coinDetailModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DetailRepositoryTest {

    private lateinit var detailRemoteDataSource: DetailRemoteDataSourceFake
    private lateinit var detailRepository: DetailRepository

    @BeforeEach
    fun setup() {
        detailRemoteDataSource = DetailRemoteDataSourceFake()
        detailRepository = DetailRepositoryImpl(detailRemoteDataSource)
    }

    @Test
    fun `GetCoinDetail, return coin detail`() = runBlocking {
        val actualResult = detailRepository.getCoinDetail("bitcoin")
        val expectedResult = coinDetailModel.right()

        assertThat(actualResult.isRight()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetCoinDetail, return Api Exception`() = runBlocking {
        val actualResult = detailRepository.getCoinDetail("eth")
        val expectedResult = apiException.left()

        assertThat(actualResult.isLeft()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}