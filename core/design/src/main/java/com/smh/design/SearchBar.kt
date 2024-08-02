package com.smh.design

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smh.design.extension.noRippleClick
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview

@Composable
fun SearchBar(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onClear: () -> Unit,
    onValueChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val iconTintColor by animateColorAsState(
        targetValue = if (isFocused) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary,
        label = "TintColor"
    )
    val isSearchNotEmpty by remember(value) {
        derivedStateOf {
            value.isNotEmpty()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(percent = 50),
                ambientColor = MaterialTheme.colorScheme.inversePrimary,
                spotColor = MaterialTheme.colorScheme.inversePrimary
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(percent = 50)
            )
            .clip(RoundedCornerShape(percent = 50)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "SearchIcon",
            tint = iconTintColor,
            modifier = Modifier
                .padding(
                    start = dimensions.space16,
                    end = dimensions.space8
                )
                .size(20.dp)
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
                value = value,
                onValueChange = onValueChanged,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        isFocused = false
                        focusManager.clearFocus()
                    }
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                cursorBrush = Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary))
            )

            if (!isSearchNotEmpty) {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (isSearchNotEmpty) {
            Box(
                modifier = Modifier
                    .size(dimensions.size48)
                    .noRippleClick{
                        onClear()
                        focusManager.clearFocus()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "SearchIcon",
                    tint = iconTintColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun SearchBarPreview() {
    CoinTrackerTheme {
        SearchBar(
            value = "",
            onClear = {},
            onValueChanged = {},
            placeholder = "Search"
        )
    }
}

@NightPreview
@Composable
private fun SearchBarNightPreview() {
    CoinTrackerTheme {
        SearchBar(
            value = "BTC",
            onClear = {},
            onValueChanged = {},
            placeholder = "Search"
        )
    }
}