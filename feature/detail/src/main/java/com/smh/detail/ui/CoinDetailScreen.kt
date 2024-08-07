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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
import com.smh.design.utility.DayTallPreview
import com.smh.detail.R
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
internal fun CoinDetailContent(
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
            label = stringResource(id = R.string.coin_detail_label),
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
                        endDate = "21/06/24",
                        modifier = Modifier.testTag(stringResource(id = R.string.sparkline_test_tag))
                    )

                    CoinDetailTitleText(text = stringResource(id = R.string.overview_title))

                    CoinDetailDescription(
                        text = detail.description
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = stringResource(id = R.string.current_price_label),
                        leadingStatValue = detail.currentPrice.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = stringResource(id = R.string.market_cap_label),
                        trailingStatValue = detail.marketCap.formattedWithAbbreviations(),
                        trailingStatPercentageChange = null,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = stringResource(id = R.string.rank_label),
                        leadingStatValue = detail.marketCapRank.toString(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = stringResource(id = R.string.volume_label),
                        trailingStatValue = detail.totalVolume.formattedWithAbbreviations(),
                        trailingStatPercentageChange = null,
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = dimensions.space24))

                    CoinDetailTitleText(text = stringResource(id = R.string.additional_title))

                    CoinDetailStatistics(
                        leadingStatLabel = stringResource(id = R.string.twenty_four_h_high_label),
                        leadingStatValue = detail.high24h.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = detail.priceChangePercentage24h,
                        trailingStatLabel = stringResource(id = R.string.twenty_four_h_low_label),
                        trailingStatValue = detail.low24h.asCurrencyWith6Decimals(),
                        trailingStatPercentageChange = detail.marketCapChangePercentage24h,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = stringResource(id = R.string.twenty_four_h_price_change_label),
                        leadingStatValue = detail.priceChange24h.asCurrencyWith6Decimals(),
                        leadingStatPercentageChange = detail.priceChangePercentage24h,
                        trailingStatLabel = stringResource(id = R.string.twenty_four_h_marketcap_change_label),
                        trailingStatValue = detail.marketCapChange24h.formattedWithAbbreviations(),
                        trailingStatPercentageChange = detail.marketCapChangePercentage24h,
                    )

                    CoinDetailStatistics(
                        leadingStatLabel = stringResource(id = R.string.block_time_label),
                        leadingStatValue = detail.blockTimeInMinutes.toString(),
                        leadingStatPercentageChange = null,
                        trailingStatLabel = stringResource(id = R.string.hashing_algorithm_label),
                        trailingStatValue = detail.hashingAlgorithm,
                        trailingStatPercentageChange = null,
                    )

                    HorizontalDivider(modifier = Modifier.padding(top = dimensions.space24))

                    UrlButton(
                        text = stringResource(id = R.string.website_label),
                        url = detail.websiteUrl,
                        modifier = Modifier.padding(horizontal = dimensions.space8)
                    )

                    UrlButton(
                        text = stringResource(id = R.string.reddit_label),
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

@DayTallPreview
@NightTallPreview
@Composable
private fun CoinDetailScreenNightPreview() {
    CoinTrackerTheme {
        CoinDetailContent(
            uiState = CoinDetailUiState(coinDetail = CoinDetailModel.btc),
            onUserEvent = {}
        )
    }
}