package com.jabozaroid.abopay.core.designsystem.component.cardinformation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.InputVisualTransformation
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppOutlinedButton
import com.jabozaroid.abopay.core.designsystem.component.AppTextField
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun CardInformation(
    model: CardInformationUiModel,
    modifier: Modifier = Modifier,
    isEnableCardNumber: Boolean = true,
    isEnableCVV2: Boolean = true,
    isEnableOtpBox: Boolean = true,
    isEnableMonth: Boolean = true,
    isEnableYear: Boolean = true,
    isEnableCardNumberTextFiledTrailingIcon: Boolean = true,
    isEnableCardNumberTextFiledLeadingIcon: Boolean = true,
    onChangeCardTextFiled: (String) -> Unit = {},
    onIconClicked: () -> Unit = {},
    onOtpRequest: () -> Unit = {},
    onChangeCvv2: (String) -> Unit = {},
    onChangeMonth: (String) -> Unit = {},
    onChangeYear: (String) -> Unit = {},
    onChangeOtpTextFiled: (secondPassword: String) -> Unit = {},
    cardNumber: MutableState<String> = mutableStateOf(""),
) {
    Column(
        modifier = modifier.wrapContentHeight()
    ) {
        if (isEnableCardNumber) {
            CardContent(
                model = model,
                onChangeCardTextFiled = onChangeCardTextFiled,
                onIconClicked = onIconClicked,
                cardNumber = cardNumber,
                isEnableCardNumberTextFiledLeadingIcon = isEnableCardNumberTextFiledLeadingIcon,
                isEnableCardNumberTextFiledTrailingIcon = isEnableCardNumberTextFiledTrailingIcon
            )
        }
        if (isEnableOtpBox) OtpContainer(model = model, onOtpRequest = {
            onOtpRequest()
        }, onChangeOtpTextFiled = {
            onChangeOtpTextFiled(it)
        })

        ExpireDateContent(
            model = model,
            modifier = Modifier.padding(
                end = Dimens.size_8,
                start = Dimens.size_10,
                top = Dimens.size_8
            ),
            isEnableCVV2 = isEnableCVV2,
            onChangeCvv2 = {
                Log.d("TAG", "CardInformationCvv2: ${it}")
                onChangeCvv2(it)
            },
            onChangeMonth = onChangeMonth,
            onChangeYear = onChangeYear,
            isEnableYear = isEnableYear,
            isEnableMonth = isEnableMonth

        )
    }
}

@Composable
fun CardContent(
    model: CardInformationUiModel,
    onIconClicked: () -> Unit,
    onChangeCardTextFiled: (String) -> Unit,
    cardMask: String = "0000  0000  0000  0000",
    maskNumber: Char = '0',
    cardNumber: MutableState<String> = mutableStateOf(""),
    isEnableCardNumberTextFiledTrailingIcon: Boolean = true,
    isEnableCardNumberTextFiledLeadingIcon: Boolean = true,
) {
    var number by rememberSaveable { mutableStateOf(cardNumber) }
    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.size_16),
        value = number.value,//cardNumber.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textDirection = TextDirection.Ltr,
        onValueChange = {
//            if (it.all(Char::isDigit)) {
                cardNumber.value = it.take(cardMask.count { char ->
                    char == maskNumber
                })
                onChangeCardTextFiled(it.take(cardMask.count { char ->
                    char == maskNumber
                }))
                number = cardNumber
//            }
        },
        visualTransformation = InputVisualTransformation(cardMask, maskNumber),
        label = stringResource(id = com.jabozaroid.abopay.core.common.R.string.card_number),
        isError = !model.card.errorMessage.aboPayStringResource().isNullOrBlank(),
        supportingText = model.card.errorMessage.aboPayStringResource(),
        placeHolder = "- - - -  - - - -  - - - -  - - - -",
        leadingIcon = {
            if (isEnableCardNumberTextFiledLeadingIcon) Icon(painter = painterResource(id = R.drawable.ic_card_design_system),
                contentDescription = "leading icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onIconClicked()
                    })
        },
        trailingIcon = {
            if (isEnableCardNumberTextFiledTrailingIcon) model.card.icon?.let {
                Image(painter = painterResource(id = it),
                    contentDescription = "leading icon",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onIconClicked()
                        })
            }

        })
}

@Composable
fun OtpContainer(
    model: CardInformationUiModel,
    onOtpRequest: () -> Unit,
    onChangeOtpTextFiled: (secondPassword: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = Dimens.size_8, start = Dimens.size_16),
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .weight(0.6f)
                .align(
                    if (!model.otpModel.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty()
                    ) Alignment.CenterVertically else Alignment.Bottom
                )

        ) {
            AppOutlinedButton(modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = Dimens.size_56)
                .align(Alignment.Center),
                contentPadding = PaddingValues(horizontal = Dimens.size_0),
                onClick = {
                    if (model.otpModel.enableOtpRequest) {
                        onOtpRequest()
                    }
                },
                text = {
                        Text(
                            text = model.otpModel.timeLeft,
                            maxLines = 1,
                            style = AppTheme.typography.text_10PX_13SP_B
                        )
                })
        }
        Box(

            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimens.size_8)
        ) {
            val maxOtpLength = 6
            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier.align(Alignment.Center),
                value = model.otpModel.otpCode,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        if (it.length <= maxOtpLength) {
                            onChangeOtpTextFiled(it)
                        }
                    }
                },
                isError = !model.otpModel.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = model.otpModel.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.otp_title)
            )
        }
    }
}

@Composable
private fun ExpireDateContent(
    model: CardInformationUiModel,
    modifier: Modifier,
    onChangeCvv2: (String) -> Unit,
    onChangeMonth: (String) -> Unit,
    onChangeYear: (String) -> Unit,
    isEnableCVV2: Boolean = true,
    isEnableYear: Boolean = true,
    isEnableMonth: Boolean = true,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.size_10),
        horizontalArrangement = Arrangement.Absolute.spacedBy((-5).dp),

        ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .padding(end = Dimens.size_8, start = Dimens.size_6)
                .align(
                    if (!model.card.cvv2.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty() || !model.card.month.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty()
                    ) Alignment.Top else Alignment.CenterVertically
                )
        ) {
            val maxYearLength = 2
            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier,
                enabled = isEnableYear,
                value = model.card.year.number ?: "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        if (it.length <= maxYearLength) {
                            onChangeYear(it)
                        }
                    }
                },
                label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.year),
                isError = !model.card.year.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = model.card.year.errorMessage.aboPayStringResource()
            )

        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = Dimens.size_8)
                .align(
                    if (!model.card.cvv2.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty() || !model.card.year.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty()
                    ) Alignment.Top else Alignment.CenterVertically
                )
        ) {
            val maxMonthLength = 2
            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier
                    .padding(end = 0.dp),
                value = model.card.month.number ?: "",
                enabled = isEnableMonth,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        if (it.length <= maxMonthLength) {
                            onChangeMonth(it)
                        }
                    }
                },
                label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.month),
                isError = !model.card.month.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = model.card.month.errorMessage.aboPayStringResource()
            )
        }
        if (isEnableCVV2) Box(
            modifier = Modifier
                .weight(0.6f)
                .padding(horizontal = Dimens.size_8)
                .align(
                    if (!model.card.year.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty() || !model.card.month.errorMessage
                            .aboPayStringResource()
                            .isNullOrEmpty()
                    ) Alignment.Top else Alignment.CenterVertically
                )
        ) {
            val maxCvv2Length = 4
            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier,
                value = model.card.cvv2.number,
                placeHolder = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.cvv2_hint),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        if (it.length <= maxCvv2Length) {
                            onChangeCvv2(it)
                        }
                    }
                },
                isError = !model.card.cvv2.errorMessage.aboPayStringResource().isNullOrEmpty(),
                supportingText = model.card.cvv2.errorMessage.aboPayStringResource(),
                label = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.cvv2),
            )
        }


    }

}

@Preview
@Composable
fun PreviewContentCard() {
    AppTheme {
        AppBackground(Modifier) {
            CardInformation(
                modifier = Modifier.padding(Dimens.size_24),
                onChangeCardTextFiled = {},
                onChangeCvv2 = {},
                onChangeOtpTextFiled = {},
                onChangeYear = {},
                onOtpRequest = {},
                onChangeMonth = {},
                onIconClicked = {},
                model = CardInformationUiModel()
            )
        }
    }
}
