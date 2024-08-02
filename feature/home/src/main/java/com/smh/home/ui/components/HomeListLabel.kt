package com.smh.home.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.extension.noRippleClick
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions

@Composable
fun HomeListLabel(
    modifier: Modifier = Modifier,
    text: String,
    showSorting: Boolean,
    isDownSorting: Boolean,
    onSortingChange: (Boolean) -> Unit
) {
    val arrowDegree by animateFloatAsState(
        targetValue = if(isDownSorting) 0f else 180f,
        label = "Sorting"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.noRippleClick { onSortingChange(!isDownSorting) }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = modifier
        )

        if (showSorting) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Sorting",
                modifier = Modifier
                    .size(dimensions.size14)
                    .graphicsLayer {
                        rotationZ = arrowDegree
                    }
            )
        }
    }
}

@DayPreview
@Composable
private fun HomeListLabelPreview() {
    CoinTrackerTheme {
        Surface {
            HomeListLabel(
                text = "Coin",
                showSorting = true,
                isDownSorting = true,
                onSortingChange = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun HomeListLabelNightPreview() {
    CoinTrackerTheme {
        Surface {
            HomeListLabel(
                text = "Coin",
                showSorting = true,
                isDownSorting = false,
                onSortingChange = {}
            )
        }
    }
}