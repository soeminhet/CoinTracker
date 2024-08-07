package com.smh.detail

import arrow.core.left
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.smh.detail.data.repository.DetailRepositoryImpl
import com.smh.detail.domain.repository.DetailRepository
import com.smh.detail.domain.usecase.GetCoinDetailUseCase
import com.smh.detail.fake.DetailRemoteDataSourceFake
import com.smh.detail.fake.coinDetailModel
import com.smh.detail.helper.TestCoroutineExtension
import com.smh.testing.apiException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
class GetCoinDetailUseCaseTest {

    private lateinit var detailRemoteDataSource: DetailRemoteDataSourceFake
    private lateinit var detailRepository: DetailRepository
    private lateinit var useCase: GetCoinDetailUseCase

    @BeforeEach
    fun setup() {
        detailRemoteDataSource = DetailRemoteDataSourceFake()
        detailRepository = DetailRepositoryImpl(detailRemoteDataSource)
        useCase = GetCoinDetailUseCase(StandardTestDispatcher(), detailRepository)
    }

    @Test
    fun `GetCoinDetail, return coin detail`() = runTest {
        val actualResult = useCase.execute("bitcoin")
        val expectedResult = coinDetailModel.right()

        assertThat(actualResult.isRight()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `GetCoinDetail, return Api Exception`() = runTest {
        val actualResult = useCase.execute("eth")
        val expectedResult = apiException.left()

        assertThat(actualResult.isLeft()).isTrue()
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}