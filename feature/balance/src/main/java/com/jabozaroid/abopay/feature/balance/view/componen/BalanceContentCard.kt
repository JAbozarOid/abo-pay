package com.jabozaroid.abopay.feature.balance.view.componen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.CardInformation
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.balance.model.BalanceUiModel

@Composable
internal fun BalanceContentCard(
    state: BalanceUiModel,
    modifier: Modifier,
    cardNumber: MutableState<String> = mutableStateOf(""),
    onIconClicked: () -> Unit,
    onChangeCardTextFiled: (String) -> Unit,
    onChangeOtpTextFiled: (secondPassword: String) -> Unit,
    onOtpRequest: () -> Unit = {},
    onChangeCvv2: (String) -> Unit,
    onChangeMonth: (String) -> Unit,
    onChangeYear: (String) -> Unit,
    onClickConfirmButton: () -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = Dimens.size_4,
                start = Dimens.size_8,
                end = Dimens.size_8,
                bottom = Dimens.size_8,
            )
            .clip(RoundedCornerShape(Dimens.size_12))
            .background(AppTheme.colorScheme.background)
    ) {
        BalanceDescription(
            modifier = Modifier
                .padding(horizontal = Dimens.size_24)
                .padding(top = Dimens.size_24),
            state = state.vat
        )
        CardInformation(
            modifier = Modifier.padding(top = Dimens.size_8),
            onIconClicked = onIconClicked,
            cardNumber = cardNumber,
            model = state.selectedCard,
            onOtpRequest = onOtpRequest,
            onChangeOtpTextFiled = onChangeOtpTextFiled,
            onChangeCardTextFiled = onChangeCardTextFiled,
            onChangeMonth = onChangeMonth,
            onChangeYear = onChangeYear,
            onChangeCvv2 = onChangeCvv2,
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            enabled = state.selectedCard.enableBalanceConfirmButton(),
            onClick = {
                onClickConfirmButton()
            },
            modifier = Modifier
                .padding(Dimens.size_8)
                .fillMaxWidth(),
        ) {
            Text(
                text = aboPayStringResource(R.string.inquiry),
                style = AppTheme.typography.text_12PX_16SP_M
            )
            }

    }
}