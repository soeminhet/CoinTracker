package com.smh.detail.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.smh.design.ImageLoader
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.detail.domain.model.CoinDetailModel

@Composable
fun CoinDetailTopBar(
    modifier: Modifier = Modifier,
    name: String,
    symbol: String,
    image: String?,
    onBackClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = dimensions.space8)
    ) {
        TextButton(onClick = onBackClick) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Back",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensions.space8),
            modifier = Modifier.padding(end = dimensions.horizontalSpace)
        ) {
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodyMedium
            )
            if (image != null) {
                ImageLoader(
                    model = image,
                    modifier = Modifier.size(dimensions.size30)
                )
            }
        }
    }
}

@DayPreview
@Composable
private fun CoinDetailTopBarPreview(){
    CoinTrackerTheme {
        Surface {
            CoinDetailTopBar(
                name = CoinDetailModel.btc.name,
                symbol = CoinDetailModel.btc.symbol,
                image = CoinDetailModel.btc.image,
                onBackClick = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun CoinDetailTopBarNightPreview(){
    CoinTrackerTheme {
        Surface {
            CoinDetailTopBar(
                name = CoinDetailModel.btc.name,
                symbol = CoinDetailModel.btc.symbol,
                image = CoinDetailModel.btc.image,
                onBackClick = {}
            )
        }
    }
}