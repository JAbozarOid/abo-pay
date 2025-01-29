package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.balance.model.BalanceUiModel

@Composable
internal fun ContentResultData(
    state: BalanceUiModel
) {
    Column(modifier = Modifier.padding(horizontal = Dimens.size_34)) {
        HorizontalDivider(Modifier.padding(bottom = Dimens.size_24))
        Row(
            Modifier.fillMaxWidth(), Arrangement.SpaceBetween
        ) {
            Text(
                text = state.receipt.amountSeparated ?: "",
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.5f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_11PX_15SP_B.copy(textDirection = TextDirection.Ltr)

            )
            Text(
                text = aboPayStringResource(R.string.amount_title),
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.3f),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_11PX_15SP_B

            )
        }

        HorizontalDivider(Modifier.padding(top = Dimens.size_24))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = Dimens.size_24),
            Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.transactionDate ?: "",
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            Text(
                text = aboPayStringResource(R.string.date_and_time_title),
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.5f),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B
            )

        }
        Row(
            Modifier
                .fillMaxWidth(), Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.maskedPan ?: "",
                modifier = Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            Text(
                text = aboPayStringResource(R.string.card_number),
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.4f),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B
            )

        }

        Row(
            Modifier
                .fillMaxWidth(), Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.issuerName ?: "",
                modifier = Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            Text(
                text = aboPayStringResource(R.string.bank_name_title),
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.4f),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B
            )

        }

        Row(
            Modifier.fillMaxWidth(), Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.rrn ?: "",
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                Text(
                    text = aboPayStringResource(R.string.reference_number_title),
                    Modifier
                        .padding(all = Dimens.size_5)
                        .weight(0.4f),
                    color = AppTheme.colorScheme.aboTitleText,
                    maxLines = 1,
                    style = AppTheme.typography.text_10PX_13SP_B
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(), Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.trace ?: "",
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                Text(
                    text = aboPayStringResource(R.string.tracking_number_title),
                    Modifier
                        .padding(all = Dimens.size_5)
                        .weight(0.4f),
                    color = AppTheme.colorScheme.aboTitleText,
                    maxLines = 1,
                    style = AppTheme.typography.text_10PX_13SP_B
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(), Arrangement.SpaceBetween
        ) {

            Text(
                text = state.receipt.availableAmountSeparated ?: "",
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(1f)
                    .basicMarquee(),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B.copy(textDirection = TextDirection.Ltr)
            )

            Text(
                text = aboPayStringResource(R.string.available_amount_title),
                Modifier
                    .padding(all = Dimens.size_5)
                    .weight(0.8f),
                color = AppTheme.colorScheme.aboTitleText,
                maxLines = 1,
                style = AppTheme.typography.text_10PX_13SP_B
            )

        }
        HorizontalDivider(Modifier.padding(top = Dimens.size_24))

    }

}


@ThemePreviews
@Preview(showBackground = true)
@Composable
fun PreviewContentResultData() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            ContentResultData(BalanceUiModel())
        }
    }
}