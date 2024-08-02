package com.smh.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.G

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Pink = Color(0xFFFACFFC)
val Pink80 = Color(0xCCFACFFC)
val Pink40 = Color(0x66FACFFC)
val Green = Color(0xFF008F00)
val LightGreen = Color(0xFF73FA79)
val Red = Color(0xFF941651)
val LightRed = Color(0xFFFF2F92)
val Gray = Color(0xFF919191)
val LightGray = Color(0xFFC0C0C0)
val Blue = Color(0xFF007AFF)

val GreenAdaptive: Color
    @Composable
    get() {
        return if(isSystemInDarkTheme()) LightGreen else Green
    }

val RedAdaptive: Color
    @Composable
    get() {
        return if(isSystemInDarkTheme()) LightRed else Red
    }
