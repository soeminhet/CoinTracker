package com.smh.home.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.home.R
import com.smh.home.domain.model.CoinModel

@Composable
fun PortfolioSheetTitle() {
    Text(
        text = stringResource(id = R.string.edit_portfolio),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = dimensions.horizontalSpace)
            .padding(top = dimensions.space12)
    )
}

@DayPreview
@Composable
private fun PortfolioSheetTitlePreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetTitle()
        }
    }
}

@NightPreview
@Composable
private fun PortfolioSheetTitleNightPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetTitle()
        }
    }
}