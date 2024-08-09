package com.smh.benchmark.extensions

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

internal fun MacrobenchmarkScope.scrollDownUpAndNavigate() {
    device.waitForIdle()

    device.wait(Until.gone(By.text("CoinTrackerLoading")), 5000)
    device.wait(Until.hasObject(By.text("BTC")), 5000)

    val list = device.findObject(By.res("HomeCoinsList"))
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    device.waitForIdle()
    list.fling(Direction.UP)

    list.children[0].click()
    device.wait(Until.hasObject(By.text("Overview")), 5000)
}