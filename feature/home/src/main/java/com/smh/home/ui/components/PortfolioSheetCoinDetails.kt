package com.smh.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.smh.design.theme.CoinTrackerTheme
import com.smh.design.theme.dimensions
import com.smh.design.utility.DayPreview
import com.smh.design.utility.NightPreview
import com.smh.design.utility.asCurrencyWith6Decimals
import com.smh.home.R
import com.smh.home.domain.model.CoinModel

@Composable
fun PortfolioSheetCoinDetails(
    coin: CoinModel,
    amount: String,
    currentValue: Double,
    onAmountChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimensions.horizontalSpace)
    ) {
        PortfolioSheetDetailRow(
            label = "Current price of ${coin.symbol}:",
            value = coin.displayPrice
        )
        PortfolioSheetDetailRow(
            label = "Amount holding:",
            value = amount,
            isEditable = true,
            onValueChanged = onAmountChanged
        )
        PortfolioSheetDetailRow(
            label = "Current value:",
            value = currentValue.asCurrencyWith6Decimals()
        )
    }
}

@Composable
private fun PortfolioSheetDetailRow(
    label: String,
    value: String,
    isEditable: Boolean = false,
    onValueChanged: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(vertical = dimensions.space10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        if (isEditable) {
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                textStyle = MaterialTheme.typography.titleMedium
                    .copy(
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.inversePrimary
                    ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.testTag(stringResource(id = R.string.portfolio_amount_test_tag))
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@DayPreview
@Composable
private fun PortfolioSheetCoinDetailsPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetCoinDetails(
                coin = CoinModel.btc,
                amount = "",
                currentValue = 10000.0,
                onAmountChanged = {}
            )
        }
    }
}

@NightPreview
@Composable
private fun PortfolioSheetCoinDetailsNightPreview() {
    CoinTrackerTheme {
        Surface {
            PortfolioSheetCoinDetails(
                coin = CoinModel.btc,
                amount = "100",
                currentValue = 10000.0,
                onAmountChanged = {}
            )
        }
    }
}