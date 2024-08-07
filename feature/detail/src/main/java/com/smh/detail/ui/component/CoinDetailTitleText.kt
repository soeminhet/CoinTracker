package com.smh.detail.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.detail.domain.model.CoinDetailModel

@Composable
fun CoinDetailTitleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.horizontalSpace)
            .padding(top = dimensions.space16),
    )
}

@DayPreview
@NightPreview
@Composable
private fun CoinDetailTitleTextPreview(){
    CoinTrackerTheme {
        Surface {
            CoinDetailTitleText(text = "Overall")
        }
    }
}