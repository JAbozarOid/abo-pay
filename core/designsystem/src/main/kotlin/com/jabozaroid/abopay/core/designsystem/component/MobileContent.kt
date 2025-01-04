package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.InputVisualTransformation
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.MobileNumberUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.PhoneNumberItem
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens


//region Mobile Content
@Composable
fun MobileContent(
    mobileNumberUiModel: MobileNumberUiModel,
    userPhoneNumbers: List<PhoneNumberItem> = listOf(),
    phoneNumberStateValue: MutableState<String>,
    onPhoneNumberValidate: (value: String) -> Unit = {},
    onDeleteUserFavoritePhoneNumber: (value: String) -> Unit = {},
    onPhoneNumberError: (value: String) -> Unit = {},
    onPhoneNumberErrorStatus: (value: String) -> Unit = {},
    onClearOperatorStatusByTextEmpty: (value: Int?) -> Unit = {},
    onFindOperatorIndexByUserInput: (value: String) -> Unit = {},
    onPhoneNumberValueChange: (value: String) -> Unit = {},
) {
    val phoneMask = "0000  000  00  00"
    val maskNumber = '0'
    val isTextFieldEmpty = phoneNumberStateValue.value.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Dimens.size_4)
    ) {

        AppTextField(
            textDirection = TextDirection.Ltr,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_4),
            value = phoneNumberStateValue.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = {
                if (it.all(Char::isDigit)) {
                    if (it.length <= 11) {
                        phoneNumberStateValue.value = it
                        onPhoneNumberError(phoneNumberStateValue.value)
                        onPhoneNumberErrorStatus(phoneNumberStateValue.value)
                        onFindOperatorIndexByUserInput(phoneNumberStateValue.value)
                        onPhoneNumberValueChange(phoneNumberStateValue.value)
                    }
                    if (phoneNumberStateValue.value.isEmpty()) {
                        onClearOperatorStatusByTextEmpty(null)
                    }
                }
            },
            visualTransformation = InputVisualTransformation(phoneMask, maskNumber),
            label = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.phone_number),
            placeHolder = "- -    - -    - - -    - - - -",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sim_2),
                    contentDescription = "leading icon", modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            //todo : get the user mobiel phone number which is login with that
                            phoneNumberStateValue.value = "09359776687"
                            onPhoneNumberValidate(phoneNumberStateValue.value)
                            onFindOperatorIndexByUserInput(phoneNumberStateValue.value)
                        })
            },
            trailingIcon = {},
            supportingText = mobileNumberUiModel.errorMessage.aboPayStringResource(),
            isError = mobileNumberUiModel.errorStatus

        )

        //region handle mobile content state and operators icon state
        if (isTextFieldEmpty && phoneNumberStateValue.value.length < 11) {
            // list of the phone numbers which user bought charge before
            UserFavouriteMobilesContent(
                list = userPhoneNumbers,
                phoneNumberStateValue = phoneNumberStateValue,
                onPhoneNumberValidate = onPhoneNumberValidate,
                onDeleteUserFavoritePhoneNumber = onDeleteUserFavoritePhoneNumber,
                onFindOperatorIndexByUserInput = onFindOperatorIndexByUserInput
            )
            onPhoneNumberValidate("")
        }
        //endregion

    }
}
//endregion