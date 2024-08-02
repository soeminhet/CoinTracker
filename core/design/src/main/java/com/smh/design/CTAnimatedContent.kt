package com.smh.design

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.smh.design.utility.CLAnimationFast

interface CTAnimatedContentState { val index: Int }

@Composable
fun <S: CTAnimatedContentState> CTAnimatedContent(
    modifier: Modifier = Modifier,
    targetState: S,
    content: @Composable AnimatedContentScope.(targetState: S) -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            val animationSpec: TweenSpec<IntOffset> = tween(CLAnimationFast)
            val direction =
                if (targetState.index > initialState.index) AnimatedContentTransitionScope.SlideDirection.Left
                else AnimatedContentTransitionScope.SlideDirection.Right
            slideIntoContainer(
                towards = direction,
                animationSpec = animationSpec,
            ) togetherWith slideOutOfContainer(
                towards = direction,
                animationSpec = animationSpec
            )
        },
        label = "CLAnimatedContent",
        content = content
    )
}