package com.smh.design.utility

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "CoinTrackerDayPreview", uiMode = UI_MODE_NIGHT_NO)
annotation class DayPreview

@Preview(name = "CoinTrackerNightPreview", uiMode = UI_MODE_NIGHT_YES)
annotation class NightPreview

@Preview(name = "CoinTrackerDayPreview", uiMode = UI_MODE_NIGHT_NO, heightDp = 2000)
annotation class DayTallPreview

@Preview(name = "CoinTrackerNightPreview", uiMode = UI_MODE_NIGHT_YES, heightDp = 2000)
annotation class NightTallPreview