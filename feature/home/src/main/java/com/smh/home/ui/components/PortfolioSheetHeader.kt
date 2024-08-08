package com.smh.home.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.GreenAdaptive
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.R

@Composable
fun PortfolioSheetHeader(
    showSaveSuccess: Boolean,
    saveButtonAlpha: Float,
    enableSaveButton: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    resetState: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.space4),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onDismiss
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close)
            )
        }

        Crossfade(
            targetState = showSaveSuccess,
            label = "PortfolioSaveButton",
            modifier = Modifier
                .width(dimensions.size60)
                .height(dimensions.size44)
        ) {
            if (it) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Saved",
                        tint = GreenAdaptive
                    )
                }
            } else {
                TextButton(
                    onClick = {
                        onSave()
                        resetState()
                    },
                    enabled = enableSaveButton,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.graphicsLayer { alpha = saveButtonAlpha }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }
}

@DayPreview
@Composable
private fun PortfolioSheetHeaderPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetHeader(
                showSaveSuccess = false,
                saveButtonAlpha = 1f,
                enableSaveButton = true,
                onDismiss = {},
                onSave = {},
                resetState = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun PortfolioSheetHeaderNightPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetHeader(
                showSaveSuccess = true,
                saveButtonAlpha = 1f,
                enableSaveButton = true,
                onDismiss = {},
                onSave = {},
                resetState = {}
            )
        }
    }
}