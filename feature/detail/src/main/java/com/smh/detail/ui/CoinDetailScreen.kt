package com.smh.detail.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.smh.design.CoinTrackerLoading
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.NightTallPreview
import com.smh.design.utility.asCurrencyWith6Decimals
import com.smh.design.utility.formattedWithAbbreviations
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.ui.component.CoinDetailDescription
import com.smh.detail.ui.component.CoinDetailStatistics
import com.smh.detail.ui.component.CoinDetailTitleText
import com.smh.detail.ui.component.CoinDetailTopBar
import com.smh.design.UrlButton
import com.smh.detail.ui.component.SparkLine7dChartView
import kotlinx.coroutines.delay

@Composable
fun CoinDetailScreen(
    coinId: String,
    onBack: () -> Unit,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(coinId) {
        delay(300) // To avoid flickering when navigation
        viewModel.fetchCoinDetail(coinId)
    }

    CoinDetailContent(
        uiState = uiState,
        onUserEvent = {
            when (it) {
                CoinDetailUserEvent.OnBack -> onBack()
            }
        }
    )
}

@Composable
private fun CoinDetailContent(
    uiState: CoinDetailUiState,
    onUserEvent: (CoinDetailUserEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    if (uiState.loading) CoinTrackerLoading()

    Scaffold(
        topBar = {
            CoinDetailTopBar(
                onBackClick = dropUnlessResumed { onUserEvent(CoinDetailUserEvent.OnBack) },
                name = uiState.coinDetail?.name.orEmpty(),
                symbol = uiState.coinDetail?.symbol.orEmpty(),
                image = uiState.coinDetail?.image
            )
        }
    ) {
        Crossfade(
            targetState = uiState.coinDetail,
            label = "CoinDetail",
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) { detail ->
            if(detail == null) {
                Box(modifier = Modifier.fillMaxSize())
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    SparkLine7dChartView(
                        data = detail.sparkline,
                        startDate = "14/06/24",
                        endDate = "21/06/24"
                    )

                    CoinDetailTitleText(text = "Overview")

                    CoinDetailDescription(
                        text = detail.description
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = "Current Price",
                        leadingStatValue = detail.currentPrice.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = "Market Capitalization",
                        trailingStatValue = detail.marketCap.formattedWithAbbreviations(),
                        trailingStatPercentageChange = null,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = "Rank",
                        leadingStatValue = detail.marketCapRank.toString(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = "Volume",
                        trailingStatValue = detail.totalVolume.formattedWithAbbreviations(),
                        trailingStatPercentageChange = null,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = dimensions.space24))

                    CoinDetailTitleText(text = "Additional")

                    CoinDetailStatistics(
                        leadingStatLabel = "24h High",
                        leadingStatValue = detail.high24h.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = detail.priceChangePercentage24h,
                        trailingStatLabel = "24h Low",
                        trailingStatValue = detail.low24h.asCurrencyWith6Decimals(),
                        trailingStatPercentageChange = detail.marketCapChangePercentage24h,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = "24h Price Change",
                        leadingStatValue = detail.priceChange24h.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = detail.priceChangePercentage24h,
                        trailingStatLabel = "24h MarketCap Change",
                        trailingStatValue = detail.marketCapChange24h.formattedWithAbbreviations(),
                        trailingStatPercentageChange = detail.marketCapChangePercentage24h,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = "Block Time",
                        leadingStatValue = detail.blockTimeInMinutes.toString(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = "Hashing Algorithm",
                        trailingStatValue = detail.hashingAlgorithm,
                        trailingStatPercentageChange = null,
                    )

                    HorizontalDivider(modifier = Modifier.padding(top = dimensions.space24))

                    UrlButton(
                        text = "Website",
                        url = detail.websiteUrl,
                        modifier = Modifier.padding(horizontal = dimensions.space8)
                    )

                    UrlButton(
                        text = "Reddit",
                        url = detail.redditUrl,
                        modifier = Modifier.padding(horizontal = dimensions.space8)
                    )
                }
            }
        }
    }
}

sealed interface CoinDetailUserEvent {
    data object OnBack : CoinDetailUserEvent
}

@NightTallPreview
@Composable
private fun CoinDetailScreenPreview() {
    CoinTrackerTheme {
        CoinDetailContent(
            uiState = CoinDetailUiState(coinDetail = CoinDetailModel.btc),
            onUserEvent = {}
        )
    }
}