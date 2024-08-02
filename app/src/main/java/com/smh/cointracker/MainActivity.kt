package com.smh.cointracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.smh.cointracker.route.HomeRoute
import com.smh.cointracker.route.coinDetailRoute
import com.smh.cointracker.route.homeRoute
import com.smh.design.ContentWithMessageBar
import com.smh.design.MessageBar
import com.smh.design.rememberMessageBarState
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.utility.slideNavTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val messageBarState = rememberMessageBarState()

            LaunchedEffect(key1 = Unit) {
                MessageBar.state.collect {
                    messageBarState.show(
                        message = it
                    )
                }
            }

            CoinTrackerTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentWithMessageBar(state = messageBarState) {
                        NavHost(
                            navController = navController,
                            startDestination = HomeRoute,
                            enterTransition = { slideNavTransition.enterTransition },
                            exitTransition = { slideNavTransition.exitTransition },
                            popEnterTransition = { slideNavTransition.popEnterTransition },
                            popExitTransition = { slideNavTransition.popExitTransition }
                        ) {
                            homeRoute(navController)
                            coinDetailRoute(navController)
                        }
                    }
                }
            }
        }
    }
}
