package com.smh.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.getString(id: Int) =
    activity.resources.getString(id)