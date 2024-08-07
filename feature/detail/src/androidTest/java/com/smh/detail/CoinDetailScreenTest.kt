package com.smh.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.smh.design.theme.CoinTrackerTheme
import com.smh.detail.domain.model.CoinDetailModel
import com.smh.detail.ui.CoinDetailContent
import com.smh.detail.ui.CoinDetailUiState
import com.smh.testing.getString
import org.junit.Rule
import org.junit.Test

class CoinDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun show_loadingSpinner() {
        with(composeTestRule) {
            setHomeContent(uiState = CoinDetailUiState(loading = true))

            onNodeWithContentDescription(getString(com.smh.design.R.string.loadingDesc))
                .assertExists()
        }
    }

    @Test
    fun show_coinDetail() {
        with(composeTestRule) {
            setHomeContent(uiState = CoinDetailUiState(coinDetail = CoinDetailModel.btc))

            onNodeWithContentDescription(getString(com.smh.design.R.string.loadingDesc))
                .assertDoesNotExist()

            onNodeWithTag(getString(R.string.sparkline_test_tag))
                .assertIsDisplayed()

            onNodeWithText(getString(R.string.overview_title))
                .assertIsDisplayed()

            onNodeWithTag(getString(R.string.description_test_tag))
                .assertIsDisplayed()

            onNodeWithText(getString(R.string.current_price_label))
                .performScrollTo()
                .assertIsDisplayed()

            textIsDisplay(getString(R.string.market_cap_label))

            textToScrollAndIsDisplay(getString(R.string.rank_label))

            textIsDisplay(getString(R.string.volume_label))

            textToScrollAndIsDisplay(getString(R.string.additional_title))

            textToScrollAndIsDisplay(getString(R.string.twenty_four_h_high_label))

            textIsDisplay(getString(R.string.twenty_four_h_low_label))

            textToScrollAndIsDisplay(getString(R.string.twenty_four_h_price_change_label))

            textIsDisplay(getString(R.string.twenty_four_h_marketcap_change_label))

            textToScrollAndIsDisplay(getString(R.string.block_time_label))

            textIsDisplay(getString(R.string.hashing_algorithm_label))

//            onNodeWithText(getString(R.string.website_label), substring = true, useUnmergedTree = true)
//                .assertIsDisplayed()
//
//            textToScrollAndIsDisplay(getString(R.string.reddit_label), true)
//                .assertHasClickAction()
        }
    }
}

private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.setHomeContent(uiState: CoinDetailUiState = CoinDetailUiState()) {
    setContent {
        CoinTrackerTheme {
            CoinDetailContent(
                onUserEvent = {},
                uiState = uiState
            )
        }
    }
    waitForIdle()
}

private fun SemanticsNodeInteractionsProvider.textToScrollAndIsDisplay(text: String, substring: Boolean = false): SemanticsNodeInteraction {
    return onNodeWithText(text, substring = substring)
        .performScrollTo()
        .assertIsDisplayed()
}

private fun SemanticsNodeInteractionsProvider.textIsDisplay(text: String): SemanticsNodeInteraction {
    return onNodeWithText(text)
        .assertIsDisplayed()
}

