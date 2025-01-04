package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.balance.model.BalanceUiModel


@Composable
internal fun BalanceReceiptContentBody(
    modifier: Modifier,
    state: BalanceUiModel,
    onClickConfirmButton: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Dimens.size_8,
                end = Dimens.size_8,
                bottom = Dimens.size_8,
            )
            .clip(RoundedCornerShape(Dimens.size_12))
            .background(AppTheme.colorScheme.background)
    ) {
        TitleReceiptContent(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.size_34, bottom = Dimens.size_8),
        )
        ContentResultData(state)
        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            enabled = true,
            onClick = {
                onClickConfirmButton()
            },
            modifier = Modifier
                .padding(Dimens.size_8)
                .fillMaxWidth(),
        ) {
            ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                Text(aboPayStringResource(R.string.back))
            }
        }
    }
}

@ThemePreviews
@Preview(showBackground = true)
@Composable
fun PreviewContentBody() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            BalanceReceiptContentBody(
                state = BalanceUiModel(), modifier = Modifier,
                onClickConfirmButton = {})
        }
    }
}

