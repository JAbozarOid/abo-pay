package com.jabozaroid.abopay.feature.bill.view.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
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
import com.jabozaroid.abopay.core.designsystem.component.UnsafeImageApp
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.DevicePreviews

@Composable
fun AddBillBottomSheet(
    title: String = aboPayStringResource(id = R.string.bill_main_title),
    onDismiss: () -> Unit = {},
    onBtnClickInquiry: () -> Unit = {},
    onBtnClickCancel: () -> Unit = {},
    firstTextFiledTitle: String = aboPayStringResource(id = R.string.title_optional),
    secondTextFiledTitle: String = aboPayStringResource(id = R.string.name_of_bill_place_holder),
    firstTextFiledPlaceHolder: String = aboPayStringResource(id = R.string.phone_number_with_pre),
    secondTextFiledPlaceHolder: String = aboPayStringResource(id = R.string.name_of_bill_place_holder),
    firstTextFiledMaxLength: Int = 15,
    logo: String = "",
    btn1Title: String = aboPayStringResource(id = R.string.confirm),
    btn2Title: String = aboPayStringResource(id = R.string.cancel),
    onValueChangeFirstTextFiled: (String) -> Unit = {},
    onValueChangeSecondTextFiled: (String) -> Unit = {},
    billType: Int = -1,
    error: String = "",
) {
    BottomSheetComponent(
        title = title,
        content = {
            AddBillContent(
                modifier = Modifier,
                firstTextFiledTitle = firstTextFiledTitle,
                secondTextFiledTitle = secondTextFiledTitle,
                firstTextFiledPlaceHolder = firstTextFiledPlaceHolder,
                secondTextFiledPlaceHolder = secondTextFiledPlaceHolder,
                logo = logo,
                firstTextFiledMaxLength = firstTextFiledMaxLength,
                onValueChangeFirstTextFiled = onValueChangeFirstTextFiled,
                onValueChangeSecondTextFiled = onValueChangeSecondTextFiled,
                error = error
            )
        },
        onDismiss = onDismiss,
        onBtn1Click = onBtnClickInquiry,
        onBtn2Click = onBtnClickCancel,
        btn1Text = btn1Title,
        btn2Text = btn2Title
    )
}

@Composable
internal fun AddBillContent(
    modifier: Modifier,
    firstTextFiledTitle: String,
    firstTextFiledPlaceHolder: String,
    secondTextFiledTitle: String,
    firstTextFiledMaxLength: Int,
    secondTextFiledPlaceHolder: String,
    onValueChangeFirstTextFiled: (String) -> Unit,
    onValueChangeSecondTextFiled: (String) -> Unit,
    error: String,
    logo: String,
) {
    var firstTextFiledValue by rememberSaveable { mutableStateOf("") }
    var secondTextFiledValue by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        modifier
            .fillMaxWidth()
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
                .padding(Dimens.size_18)
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

//            if (logo.isNotEmpty()) {
            UnsafeImageApp(
                darkLogo = logo,
                lightLogo = logo,
                placeholder = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature,
                modifier = Modifier
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        bottom.linkTo(firstTextFiledRef.top)
                        width = Dimension.matchParent
                    }
                    .size(Dimens.size_55, Dimens.size_55),
                contentDescription = "Item"
            )

//            }

            AppTextField(
                textDirection = TextDirection.Ltr,
                modifier = Modifier
                    .constrainAs(firstTextFiledRef) {
                        top.linkTo(iconRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.start)
                        width = Dimension.matchParent
                    }
                    .padding(top = Dimens.size_16)
                    .fillMaxWidth(),
                value = firstTextFiledValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.all(Char::isDigit)) {
                        var maxLength = firstTextFiledMaxLength
                        if (maxLength == 0)
                            maxLength = 20
                        if (it.length <= maxLength)
                            firstTextFiledValue = it
                        onValueChangeFirstTextFiled(it)
                    }
                },
                label = firstTextFiledTitle,
                placeHolder = firstTextFiledPlaceHolder,
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                isError = error.isNotEmpty(),
                supportingText = error,
                cursorColor = AppTheme.colorScheme.ivaTextFieldHint

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
                    .padding(top = Dimens.size_14),
                value = secondTextFiledValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    if (it.length < 20) {
                        secondTextFiledValue = it
                        onValueChangeSecondTextFiled(it)
                    }
                },
                label = secondTextFiledTitle,
                placeHolder = secondTextFiledPlaceHolder,
                placeHolderAlignment = Alignment.CenterEnd,
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                cursorColor = AppTheme.colorScheme.ivaTextFieldHint,
                textAlign = TextAlign.Start

            )

        }
    }
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
@DevicePreviews
internal fun PreviewBillInformationContent() {
    AppTheme {
        AppBackground(modifier = Modifier) {}

        AddBillContent(
            secondTextFiledTitle = aboPayStringResource(id = R.string.title_optional),
            firstTextFiledTitle = aboPayStringResource(id = R.string.phone_number_with_pre),
            secondTextFiledPlaceHolder = aboPayStringResource(id = R.string.name_of_bill_place_holder),
            firstTextFiledPlaceHolder = aboPayStringResource(id = R.string.phone_number_like_sample),
            logo = "", modifier = Modifier,
            firstTextFiledMaxLength = 13,
            onValueChangeFirstTextFiled = {},
            onValueChangeSecondTextFiled = {},
            error = ""
        )
    }
}
