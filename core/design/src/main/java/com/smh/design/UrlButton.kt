package com.smh.design

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.smh.design.theme.Blue
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.utility.openUrl

@Composable
fun UrlButton(
    modifier: Modifier = Modifier,
    text: String,
    url: String
) {
    val context = LocalContext.current
    TextButton(
        onClick = { context.openUrl(url) },
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(contentColor = Blue),
    ) {
        Text(
            text = text,
        )
    }
}

@DayPreview
@Composable
private fun UrlButtonPreview(){
    CoinTrackerTheme {
        Surface {
            UrlButton(
                text = "Website",
                url = "https://www.google.com"
            )
        }
    }
}

@NightPreview
@Composable
private fun UrlButtonNightPreview(){
    CoinTrackerTheme {
        Surface {
            UrlButton(
                text = "Website",
                url = "https://www.google.com"
            )
        }
    }
}