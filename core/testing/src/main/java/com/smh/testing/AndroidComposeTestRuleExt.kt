package com.smh.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <T: ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<T>, T>.getString(id: Int) =
    activity.resources.getString(id)