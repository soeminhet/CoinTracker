package com.smh.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.Red
import com.smh.design.theme.RedAdaptive
import com.smh.design.theme.White
import com.smh.design.theme.dimensions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object MessageBar {
    private val _state = MutableSharedFlow<String>()
    val state = _state.asSharedFlow()

    fun showMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.emit(message)
        }
    }
}

@Composable
fun rememberMessageBarState(): MessageBarState {
    return remember { MessageBarState() }
}

class MessageBarState {
    var message by mutableStateOf<String?>(null)
        private set
    var isShow by mutableStateOf(false)
        private set

    suspend fun show(message: String) {
        if (isShow) {
            isShow = false
            delay(300)
        }
        this.message = message
        isShow = true
        hide()
    }

    private suspend fun hide() {
        delay(3000)
        isShow = false
        delay(300)
    }
}

@Composable
fun ContentWithMessageBar(
    state: MessageBarState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        content()

        AnimatedVisibility(
            visible = state.isShow,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh
                ),
            ) {
                -it
            },
            exit = slideOutVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
            ) { -it } + fadeOut(),
            modifier = Modifier.fillMaxWidth()
        ) {
            MessageBar(
                message = state.message.orEmpty()
            )
        }
    }
}

@Composable
private fun MessageBar(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Red)
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(top = dimensions.space16)
            .padding(
                vertical = dimensions.space10,
                horizontal = dimensions.horizontalSpace
            ),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageBarPreview() {
    CoinTrackerTheme {
        MessageBar(
            message = "Something goes wrong. Pls try again later.",
        )
    }
}