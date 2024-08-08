package com.smh.cointracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.smh.cointracker.fake.btc
import com.smh.cointracker.fake.eth
import com.smh.home.R
import com.smh.testing.getString
import com.smh.testing.textIsDisplay
import com.smh.testing.textToScrollAndIsDisplay
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testApp() {
        with(composeTestRule) {
            waitForIdle()

            textIsDisplay(getString(R.string.live_prices))

            textIsDisplay(btc.symbol)

            textIsDisplay(eth.symbol)

            onNodeWithText(btc.symbol).performClick()

            // Wait for the initial delay finish
            runOnUiThread {
                Thread.sleep(300)
            }

            textIsDisplay(getString(com.smh.detail.R.string.overview_title))

            textToScrollAndIsDisplay(getString(com.smh.detail.R.string.reddit_label))

            onNodeWithText(getString(com.smh.detail.R.string.back)).performClick()

            textIsDisplay(getString(R.string.live_prices))

            onNodeWithContentDescription(getString(id = R.string.infoAndAddCircleDesc))
                .assertIsDisplayed()
                .performClick()

            onAllNodesWithText(getString(R.string.settings))
                .onFirst()
                .assertIsDisplayed()

            onAllNodesWithContentDescription(getString(R.string.close))
                .onFirst()
                .assertIsDisplayed()
                .performClick()

            onNodeWithContentDescription(getString(id = R.string.toggleShowLivePricesDesc))
                .assertIsDisplayed()
                .performClick()

            textIsDisplay(getString(R.string.portfolio))

            onNodeWithContentDescription(getString(id = R.string.infoAndAddCircleDesc))
                .assertIsDisplayed()
                .performClick()

            textIsDisplay(getString(R.string.edit_portfolio))

            onNodeWithText(btc.symbol)
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(getString(R.string.portfolio_amount_test_tag))
                .performTextInput("10")

            onNodeWithText(getString(R.string.save))
                .performClick()

            // Wait for animation complete
            runOnUiThread {
                Thread.sleep(300)
            }

            onAllNodesWithContentDescription(getString(R.string.close))
                .onFirst()
                .assertIsDisplayed()
                .performClick()

            textIsDisplay(btc.symbol)

            onNodeWithText(eth.symbol).assertDoesNotExist()
        }
    }
}