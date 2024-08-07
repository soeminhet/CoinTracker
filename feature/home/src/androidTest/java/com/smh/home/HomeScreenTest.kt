package com.smh.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.smh.design.theme.CoinTrackerTheme
import com.smh.home.fake.btc
import com.smh.home.fake.btcDominanceStat
import com.smh.home.fake.eth
import com.smh.home.fake.marketCapStat
import com.smh.home.fake.portfolioValueStat
import com.smh.home.fake.usdt
import com.smh.home.fake.volumeStat
import com.smh.home.ui.HomeContent
import com.smh.home.ui.HomeUiState
import com.smh.testing.getString
import org.junit.Rule
import org.junit.Test

internal class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val coinList = listOf(btc, eth, usdt)
    private val portfolio = listOf(btc.copy(holdingAmount = 10.0), eth.copy(holdingAmount = 5.5), usdt.copy(holdingAmount = 3.6))
    private val stats = listOf(
        marketCapStat,
        volumeStat,
        btcDominanceStat,
        portfolioValueStat
    )

    @Test
    fun toggle_LivePriceAndPortfolio() {
        with(composeTestRule) {
            setHomeContent()

            onNodeWithText(text = getString(R.string.live_prices))
                .assertIsDisplayed()

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            onNodeWithText(getString(R.string.portfolio))
                .assertIsDisplayed()
        }
    }

    @Test
    fun show_SettingsSheet() {
        with(composeTestRule) {
            setHomeContent()

            onNodeWithContentDescription(getString(id = R.string.infoAndAddCircleDesc))
                .assertIsDisplayed()
                .performClick()


            onAllNodesWithText(getString(R.string.settings))
                .onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun show_EditPortfolioSheet() {
        with(composeTestRule) {
            setHomeContent()

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            onNodeWithContentDescription(getString(id = R.string.infoAndAddCircleDesc))
                .assertIsDisplayed()
                .performClick()

            onNodeWithText(getString(R.string.edit_portfolio))
                .assertIsDisplayed()
        }
    }

    @Test
    fun show_loadingSpinner() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(loading = true))

            onNodeWithContentDescription(getString(com.smh.design.R.string.loadingDesc))
                .assertExists()
        }
    }

    @Test
    fun feed_whenRankSorting_showSortedRank() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(liveCoins = coinList.sortedBy { it.rank }))

            val showCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            coinList.sortedBy { it.rank }.forEachIndexed { index, coin ->
                showCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenRankReverseSorting_showSortedRankReverse() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(liveCoins = coinList.sortedByDescending { it.rank }))

            val showDescendingCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            coinList.sortedByDescending { it.rank }.forEachIndexed { index, coin ->
                showDescendingCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenPriceSorting_showSortedPrice() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(liveCoins = coinList.sortedBy { it.price }))

            val showCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            coinList.sortedBy { it.price }.forEachIndexed { index, coin ->
                showCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenPriceReverseSorting_showSortedPriceReverse() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(liveCoins = coinList.sortedByDescending { it.price }))

            val showDescendingCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            coinList.sortedByDescending { it.price }.forEachIndexed { index, coin ->
                showDescendingCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenHoldingSorting_showSortedHolding() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(portfolios = portfolio.sortedBy { it.holdingAmount }))

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            val showCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            portfolio.sortedBy { it.holdingAmount }.forEachIndexed { index, coin ->
                showCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenHoldingReverseSorting_showSortedHoldingReverse() {
        with(composeTestRule) {
            setHomeContent(uiState = HomeUiState(portfolios = portfolio.sortedByDescending { it.holdingAmount }))

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            val showDescendingCoinList = onNodeWithTag(getString(R.string.homeCoinList_test_tag)).onChildren()
            portfolio.sortedByDescending { it.holdingAmount }.forEachIndexed { index, coin ->
                showDescendingCoinList[index].assertTextContains(coin.symbol)
            }
        }
    }

    @Test
    fun feed_whenToggleLivePricesAndPortfolio_showStatistics() {
        with(composeTestRule) {
            setHomeContent(
                uiState = HomeUiState(
                    statistics = stats
                )
            )

            stats.take(3).forEach { value ->
                onNodeWithText(value.title).assertIsDisplayed()
            }
            onNodeWithText(stats.last().title).assertIsNotDisplayed()

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            stats.takeLast(3).forEach { value ->
                onNodeWithText(value.title).assertIsDisplayed()
            }
            onNodeWithText(stats.first().title).assertIsNotDisplayed()
        }
    }
}

private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.setHomeContent(uiState: HomeUiState = HomeUiState()) {
    setContent {
        CoinTrackerTheme {
            HomeContent(
                onUserEvent = {},
                uiState = uiState
            )
        }
    }
    waitForIdle()
}