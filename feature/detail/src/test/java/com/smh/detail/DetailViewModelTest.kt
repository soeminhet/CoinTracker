package com.smh.detail

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.smh.detail.data.repository.DetailRepositoryImpl
import com.smh.detail.domain.repository.DetailRepository
import com.smh.detail.domain.usecase.GetCoinDetailUseCase
import com.smh.detail.fake.DetailRemoteDataSourceFake
import com.smh.detail.fake.coinDetailModel
import com.smh.detail.ui.CoinDetailViewModel
import com.smh.testing.TestCoroutineExtension
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class DetailViewModelTest {

    private lateinit var detailRemoteDataSource: DetailRemoteDataSourceFake
    private lateinit var detailRepository: DetailRepository
    private lateinit var useCase: GetCoinDetailUseCase
    private lateinit var viewModel: CoinDetailViewModel

    @BeforeEach
    fun setup() {
        detailRemoteDataSource = DetailRemoteDataSourceFake()
        detailRepository = DetailRepositoryImpl(detailRemoteDataSource)
        useCase = GetCoinDetailUseCase(StandardTestDispatcher(), detailRepository)
        viewModel = CoinDetailViewModel(useCase)
    }

    @Test
    fun `CoinDetailViewModel UiState Test`() = runTest {
        viewModel.uiState.test {
            val emission1 = awaitItem()
            assertThat(emission1.loading).isFalse()

            viewModel.fetchCoinDetail("bitcoin")
            val emission2 = awaitItem()
            assertThat(emission2.loading).isTrue()

            val emission3 = awaitItem()
            assertThat(emission3.loading).isFalse()

            val emission4 = awaitItem()
            assertThat(emission4.coinDetail).isEqualTo(coinDetailModel)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `CoinDetailViewModel UiState Test Error`() = runTest {
        viewModel.uiState.test {
            val emission1 = awaitItem()
            assertThat(emission1.loading).isFalse()

            viewModel.fetchCoinDetail("eth")
            val emission2 = awaitItem()
            assertThat(emission2.loading).isTrue()

            val emission3 = awaitItem()
            assertThat(emission3.loading).isFalse()
            assertThat(emission3.coinDetail).isNull()

            cancelAndIgnoreRemainingEvents()
        }
    }
}