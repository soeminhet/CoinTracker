package com.smh.detail.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import com.smh.design.extension.noRippleClick
import com.smh.design.theme.Blue
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.detail.domain.model.CoinDetailModel

@Composable
fun CoinDetailDescription(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }

    if (text.isNotEmpty()) {
        Column(modifier = modifier.animateContentSize()) {
                Text(
                    text = AnnotatedString.fromHtml(htmlString = text),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (expanded) Int.MAX_VALUE else maxLines,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = dimensions.horizontalSpace)
                        .padding(top = dimensions.space8)
                )

                Text(
                    text = if (expanded) "Less" else "Show More",
                    style = MaterialTheme.typography.labelLarge,
                    color = Blue,
                    modifier = Modifier
                        .noRippleClick { expanded = !expanded }
                        .padding(
                            vertical = dimensions.space8,
                            horizontal = dimensions.horizontalSpace
                        )
                )
            }
    }
}

@DayPreview
@Composable
private fun CoinDetailDescriptionPreview(){
    CoinTrackerTheme {
        Surface {
            CoinDetailDescription(text = CoinDetailModel.btc.description)
        }
    }
}

@NightPreview
@Composable
private fun CoinDetailDescriptionNightPreview(){
    CoinTrackerTheme {
        Surface {
            CoinDetailDescription(text = CoinDetailModel.btc.description)
        }
    }
}

