package com.jabozaroid.abopay.feature.bill.view.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews

@Composable
internal fun PaymentInputsBillBottomSheet(
    title: String = aboPayStringResource(R.string.payment_with_id),
    onDismiss: () -> Unit = {},
    onBtnClickConfirm: () -> Unit = {},
    onBtnClickCancel: () -> Unit = {},
    firstTextFiledTitle: String = aboPayStringResource(R.string.bill_id),
    secondTextFiledTitle: String = aboPayStringResource(R.string.payment_id),
    firstTextFiledPlaceHolder: String = aboPayStringResource(R.string.bill_id_place_holder),
    secondTextFiledPlaceHolder: String = aboPayStringResource(R.string.payment_id_place_holder),
    firstTextFiledMaxLength: Int = 15,
    btn1Title: String = aboPayStringResource(R.string.confirm),
    btn2Title: String = aboPayStringResource(R.string.cancel),
    onValueChangeFirstTextFiled: (String) -> Unit = {},
    onValueChangeSecondTextFiled: (String) -> Unit = {},
    billIdError: String = "",
    payIdError: String = "",
) {
    BottomSheetComponent(
        title = title,
        content = {
            PaymentInputsBillContent(
                modifier = Modifier,
                firstTextFiledTitle = firstTextFiledTitle,
                secondTextFiledTitle = secondTextFiledTitle,
                firstTextFiledPlaceHolder = firstTextFiledPlaceHolder,
                secondTextFiledPlaceHolder = secondTextFiledPlaceHolder,
                firstTextFiledMaxLength = firstTextFiledMaxLength,
                onValueChangeFirstTextFiled = onValueChangeFirstTextFiled,
                onValueChangeSecondTextFiled = onValueChangeSecondTextFiled,
                billIdError = billIdError,
                payIdError = payIdError
            )
        },
        onDismiss = onDismiss,
        onBtn1Click = onBtnClickConfirm,
        onBtn2Click = onBtnClickCancel,
        btn1Text = btn1Title,
        btn2Text = btn2Title
    )
}

@Composable
internal fun PaymentInputsBillContent(
    modifier: Modifier,
    firstTextFiledTitle: String,
    firstTextFiledPlaceHolder: String,
    secondTextFiledTitle: String,
    firstTextFiledMaxLength: Int,
    secondTextFiledPlaceHolder: String,
    onValueChangeFirstTextFiled: (String) -> Unit,
    onValueChangeSecondTextFiled: (String) -> Unit,
    billIdError: String = "",
    payIdError: String = "",
) {
    var firstTextFiledValue by rememberSaveable { mutableStateOf("") }
    var secondTextFiledValue by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(Dimens.size_24)
            .clip(
                RoundedCornerShape(
                    topEnd = Dimens.size_12,
                    topStart = Dimens.size_12
                )
            )
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(
                    RoundedCornerShape(
                        topEnd = Dimens.size_12,
                        topStart = Dimens.size_12
                    )
                )
        ) {
            val (
                firstTextFiledRef, iconRef,
                secondTextFiledRef,
            ) = createRefs()

            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier
                    .constrainAs(firstTextFiledRef) {
                        top.linkTo(iconRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        width = Dimension.matchParent
                    }
                    .fillMaxWidth(),
                value = firstTextFiledValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        if (it.length <= 14) {
                            firstTextFiledValue = it
                            onValueChangeFirstTextFiled.invoke(it)
                        }
                    }
                },
                label = firstTextFiledTitle,
                placeHolder = firstTextFiledPlaceHolder,
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                cursorColor = AppTheme.colorScheme.aboTextFieldHint,
                isError = billIdError.isNotBlank(),
                supportingText = billIdError

            )

            AppTextField(
                modifier = Modifier
                    .constrainAs(secondTextFiledRef) {
                        top.linkTo(firstTextFiledRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        width = Dimension.matchParent
                    }
                    .fillMaxWidth()
                    .padding(vertical = Dimens.size_14),
                value = secondTextFiledValue,
                textDirection = TextDirection.Ltr,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.length <= 14) {
                        secondTextFiledValue = it
                        onValueChangeSecondTextFiled.invoke(it)
                    }
                },
                label = secondTextFiledTitle,
                placeHolder = secondTextFiledPlaceHolder,
                placeHolderAlignment = Alignment.CenterEnd,
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                cursorColor = AppTheme.colorScheme.aboTextFieldHint,
                isError = payIdError.isNotBlank(),
                supportingText = payIdError

            )

        }
    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
@DevicePreviews
internal fun PreviewPaymentInputsBillContent() {
    AppTheme {
        AppBackground(modifier = Modifier) {}

        PaymentInputsBillContent(
            secondTextFiledTitle = aboPayStringResource(id = R.string.title_optional),
            firstTextFiledTitle = aboPayStringResource(id = R.string.phone_number_with_pre),
            secondTextFiledPlaceHolder = aboPayStringResource(id = R.string.name_of_bill_place_holder),
            firstTextFiledPlaceHolder = aboPayStringResource(id = R.string.phone_number_like_sample),
            firstTextFiledMaxLength = 13,
            onValueChangeFirstTextFiled = {},
            onValueChangeSecondTextFiled = {},
            modifier = Modifier,

            )
    }
}
