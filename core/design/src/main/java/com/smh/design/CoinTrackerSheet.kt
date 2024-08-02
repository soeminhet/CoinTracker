package com.smh.design

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

typealias action = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinTrackerSheet(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.(action) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(true)
    val coroutineScope = rememberCoroutineScope()

    val onHide: action = {
        coroutineScope.launch { sheetState.hide() }
            .invokeOnCompletion { onDismiss() }
    }

    val bottomBarPadding = WindowInsets.navigationBars
        .asPaddingValues(LocalDensity.current)
        .calculateBottomPadding()

    if (show) {
        ModalBottomSheet(
            modifier = modifier.statusBarsPadding(),
            onDismissRequest = onHide,
            sheetState = sheetState,
            windowInsets = WindowInsets(top = 0.dp, bottom = bottomBarPadding),
            dragHandle = null,
            containerColor = MaterialTheme.colorScheme.background,
            content = { content(onHide) }
        )
    }
}