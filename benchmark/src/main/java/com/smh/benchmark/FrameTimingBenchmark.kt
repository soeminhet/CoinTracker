package com.smh.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import com.smh.benchmark.extensions.scrollDownUpAndNavigate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun frameTimingNone() = frameTimingMeasure(compilationMode = CompilationMode.None())

    @Test
    fun frameTimingPartial() = frameTimingMeasure(compilationMode = CompilationMode.Partial())

    @Test
    fun frameTimingFull() = frameTimingMeasure(compilationMode = CompilationMode.Full())

    private fun frameTimingMeasure(
        iterations: Int = 5,
        compilationMode: CompilationMode
    ) = benchmarkRule.measureRepeated(
        packageName = "com.smh.cointracker",
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        compilationMode = compilationMode,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        }
    ) {
        scrollDownUpAndNavigate()
    }
}