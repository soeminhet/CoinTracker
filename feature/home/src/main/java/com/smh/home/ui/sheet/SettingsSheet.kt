package com.smh.home.ui.sheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.smh.design.CoinTrackerSheet
import com.smh.design.UrlButton
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.LightGray
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.DayTallPreview
import com.smh.design.utility.NightPreview
import com.smh.design.utility.NightTallPreview
import com.smh.home.R

@Composable
fun SettingsSheet(
    show: Boolean,
    onDismiss: () -> Unit
) {
    CoinTrackerSheet(
        show = show,
        onDismiss = onDismiss
    ) { onHide ->
        SettingsSheetContent(
            onDismiss = onHide
        )
    }
}

@Composable
private fun SettingsSheetContent(
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    val showTopBarTitle by remember {
        derivedStateOf {
            scrollState.value > 100
        }
    }
    val topBarTitleAlpha by animateFloatAsState(
        targetValue = if (showTopBarTitle) 1f else 0f,
        label = "TopBarTitleAlpha"
    )

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(start = dimensions.space4)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }

                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.graphicsLayer { alpha = topBarTitleAlpha }
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(end = dimensions.space4)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Transparent
                    )
                }
            }
        },
        contentColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = dimensions.horizontalSpace)
            )

            SettingExplainSection()

            SettingsCoinGeckoSection()

            SettingsDeveloperSection(
                modifier = Modifier.padding(bottom = dimensions.space32)
            )
        }
    }
}

@Composable
private fun SettingExplainSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "CRYPTO CURRENCY TRACKER",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = dimensions.horizontalSpace)
                .padding(top = dimensions.space20)
        )

        Card(
            shape = RoundedCornerShape(0),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "CardLogo",
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.horizontalSpace)
                        .size(dimensions.size100)
                        .clip(RoundedCornerShape(percent = 10))
                )

                Text(
                    text = "This app was originally created following the Swiftful Thinking course on YouTube, utilizing MVVM architecture, Combine, and CoreData. I rewrote it for Android using MVVM Clean Architecture, Flow, and Room.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.space10)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = dimensions.horizontalSpace)
                )

                UrlButton(
                    modifier = Modifier.padding(start = dimensions.space4),
                    text = "Subscribe on Youtube",
                    url = "https://www.youtube.com/@SwiftfulThinking"
                )

                HorizontalDivider()

                UrlButton(
                    modifier = Modifier.padding(start = dimensions.space4),
                    text = "Support his coffee addiction",
                    url = "https://www.buymeacoffee.com/nicksarno"
                )
            }
        }
    }
}

@Composable
private fun SettingsCoinGeckoSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "COINGECKO",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = dimensions.horizontalSpace)
                .padding(top = dimensions.space20)
        )

        Card(
            shape = RoundedCornerShape(0),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.coin_gecko),
                        contentDescription = "CardLogo",
                        modifier = Modifier
                            .padding(horizontal = dimensions.horizontalSpace)
                            .padding(top = dimensions.horizontalSpace)
                            .size(dimensions.size100)
                            .clip(RoundedCornerShape(percent = 10))
                    )

                    Text(
                        text = "CoinGecko",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

                Text(
                    text = "This app uses data from free API from CoinGecko! prices may be slightly delayed.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.space10)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = dimensions.horizontalSpace)
                )

                UrlButton(
                    modifier = Modifier.padding(start = dimensions.space4),
                    text = "Visit CoinGecko",
                    url = "https://www.coingecko.com"
                )
            }
        }
    }
}

@Composable
private fun SettingsDeveloperSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "DEVELOPER",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = dimensions.horizontalSpace)
                .padding(top = dimensions.space20)
        )

        Card(
            shape = RoundedCornerShape(0),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "CardLogo",
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.horizontalSpace)
                        .size(dimensions.size100)
                        .clip(RoundedCornerShape(percent = 10))
                )

                Text(
                    text = "This app was developed by SoeMinHtet. It uses Jetpack Compose and is written 100% in Kotlin",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.space10)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = dimensions.horizontalSpace)
                )

                UrlButton(
                    modifier = Modifier.padding(start = dimensions.space4),
                    text = "Github",
                    url = "https://github.com/soeminhet"
                )

                HorizontalDivider()

                UrlButton(
                    modifier = Modifier.padding(start = dimensions.space4),
                    text = "Linkedin",
                    url = "www.linkedin.com/in/soe-min-htet-8344311ab"
                )
            }
        }
    }
}

@DayTallPreview
@Composable
private fun SettingsContentPreview() {
    CoinTrackerTheme {
        SettingsSheetContent(
            onDismiss = {}
        )
    }
}

@NightTallPreview
@Composable
private fun SettingsContentNightPreview() {
    CoinTrackerTheme {
        SettingsSheetContent(
            onDismiss = {}
        )
    }
}
