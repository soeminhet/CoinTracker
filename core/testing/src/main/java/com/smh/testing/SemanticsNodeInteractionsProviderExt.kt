package com.smh.testing

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo

fun SemanticsNodeInteractionsProvider.textToScrollAndIsDisplay(text: String): SemanticsNodeInteraction {
    return onNodeWithText(text)
        .performScrollTo()
        .assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.textIsDisplay(text: String): SemanticsNodeInteraction {
    return onNodeWithText(text)
        .assertIsDisplayed()
}

fun SemanticsNodeInteractionsProvider.tagIsDisplay(tag: String): SemanticsNodeInteraction {
    return onNodeWithTag(tag)
        .assertIsDisplayed()
}
