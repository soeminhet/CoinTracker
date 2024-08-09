package com.smh.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect(
            packageName = "com.smh.cointracker",
        ) {
            pressHome()
            startActivityAndWait()
            /**
             * Currently I used free api version, it cannot fetch frequently data.
             * If fetch multiple time, it return limit exceeded error.
             * So, I left this scroll down up and navigate test.
             */
            // scrollDownUpAndNavigate()
        }
    }
}