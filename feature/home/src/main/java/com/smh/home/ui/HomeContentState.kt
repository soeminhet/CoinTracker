package com.smh.home.ui

import com.smh.design.CTAnimatedContentState

enum class HomeContentState(override val index: Int) : CTAnimatedContentState {
    LivePrices(index = 0),
    Portfolio(index = 1);

    fun toggle(): HomeContentState {
        return when(this) {
            LivePrices -> Portfolio
            Portfolio -> LivePrices
        }
    }
}