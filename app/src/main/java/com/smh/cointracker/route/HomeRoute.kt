package com.smh.cointracker.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smh.home.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeRoute(
    navController: NavController
) {
    composable<HomeRoute> {
        HomeScreen(
            goToDetails = { coinId ->
                navController.navigate(CoinDetailRoute(coinId = coinId))
            }
        )
    }
}