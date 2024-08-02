package com.smh.design.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClick(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit,
) = composed {
    this.clickable(
        enabled = enabled,
        interactionSource = remember(interactionSource) { interactionSource },
        indication = null,
        onClick = onClick
    )
}