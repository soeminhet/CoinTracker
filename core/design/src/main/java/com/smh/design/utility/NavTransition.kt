package com.smh.design.utility

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

data class NavTransition(
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val popEnterTransition: EnterTransition,
    val popExitTransition: ExitTransition
)

val slideNavTransition = NavTransition(
    enterTransition = slideInHorizontally(
        initialOffsetX = { 1500 },
        animationSpec = tween(CLAnimationFast)
    ),
    exitTransition = slideOutHorizontally(
        targetOffsetX = { -1500 },
        animationSpec = tween(CLAnimationFast)
    ),
    popEnterTransition = slideInHorizontally(
        initialOffsetX = { -1500 },
        animationSpec = tween(CLAnimationFast)
    ),
    popExitTransition = slideOutHorizontally(
        targetOffsetX = { 1500 },
        animationSpec = tween(CLAnimationFast)
    )
)