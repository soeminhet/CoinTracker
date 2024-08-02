package com.smh.cointracker.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smh.detail.ui.CoinDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailRoute(
    val coinId: String
)

fun NavGraphBuilder.coinDetailRoute(
    navController: NavController
) {
    composable<CoinDetailRoute> {
        val coinId = it.toRoute<CoinDetailRoute>().coinId
        CoinDetailScreen(
            coinId = coinId,
            onBack = navController::popBackStack
        )
    }
}