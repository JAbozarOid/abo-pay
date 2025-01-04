package com.jabozaroid.abopay.feature.cardtocard.c2c.view.bottomSheet

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardinformation.CardInformation
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme

@Composable
internal fun CardToCardPaymentBottomSheet(
    state: CardInformationUiModel,
    bottomSheetTitle: String = "",
    onHideBottomSheet: () -> Unit = {},
    onBtnConfirmClicked: () -> Unit = {},
    confirmBtnText: String = "",
    onOtpRequest: () -> Unit = {},
    onChangeOtpTextFiled: (String) -> Unit = {},
    onChangeCvv2: (String) -> Unit = {},
    onChangeMonth: (String) -> Unit = {},
    onChangeYear: (String) -> Unit = {},
) {
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            CardInformation(
                model = state,
                onChangeMonth = onChangeMonth,
                onChangeYear = onChangeYear,
                onChangeCvv2 = {
                    Log.d("TAG", "CardToCardPaymentBottomSheet: CVV2 $it")
                    onChangeCvv2(it)
                },
                onChangeOtpTextFiled = onChangeOtpTextFiled,
                onOtpRequest = onOtpRequest,
                isEnableCardNumberTextFiledLeadingIcon = false,
                isEnableCardNumberTextFiledTrailingIcon = false,
                isEnableOtpBox = true,
                isEnableCVV2 = true,
                isEnableYear = false,
                isEnableMonth = false,
                isEnableCardNumber = false
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        btn1Text = confirmBtnText,
        isBtn1Enabled = state.enableCardToCardButton(),
        dualActionBtn = false
    )
}

@Preview
@Composable
@ThemePreviews
fun PaymentBottomSheetPreview() {
    AppTheme {
        CardToCardPaymentBottomSheet(CardInformationUiModel())
    }
}