package com.jabozaroid.abopay.feature.bill.view.bottomSheet


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
internal fun EditeBillBottomSheet(
    title: String = aboPayStringResource(R.string.edite_bill),
    onDismiss: () -> Unit = {},
    onBtnClickEdite: () -> Unit = {},
    onBtnClickCancel: () -> Unit = {},
    firstTextFiledTitle: String = aboPayStringResource(R.string.title_optional),
    secondTextFiledTitle: String = aboPayStringResource(R.string.name_of_bill_place_holder),
    secondTextFiledPlaceHolder: String = "",
    logo: String = "",
    btn1Title: String = aboPayStringResource(id = R.string.confirm),
    btn2Title: String = aboPayStringResource(id = R.string.cancel),
    onValueChangeFirstTextFiled: (String) -> Unit = {},
    onValueChangeSecondTextFiled: (String) -> Unit = {},
    billType: Int = -1,
    error: String = "",
    billId: String ="",
    billName: String = "",
) {
    BottomSheetComponent(
        title = title,
        content = {
            EditeBillContent(
                modifier = Modifier,
                firstTextFiledTitle = firstTextFiledTitle,
                secondTextFiledTitle = secondTextFiledTitle,
                billId =billId,
                billName = billName,
                secondTextFiledPlaceHolder = secondTextFiledPlaceHolder,
                logo = logo,
                onValueChangeFirstTextFiled = onValueChangeFirstTextFiled,
                onValueChangeSecondTextFiled = onValueChangeSecondTextFiled,
                error = error
            )
        },
        onDismiss = onDismiss,
        onBtn1Click = onBtnClickEdite,
        onBtn2Click = onBtnClickCancel,
        btn1Text = btn1Title,
        btn2Text = btn2Title
    )
}

@Composable
internal fun EditeBillContent(
    modifier: Modifier,
    firstTextFiledTitle: String,
    secondTextFiledTitle: String,
    secondTextFiledPlaceHolder: String,
    onValueChangeFirstTextFiled: (String) -> Unit,
    onValueChangeSecondTextFiled: (String) -> Unit,
    error: String,
    logo: String,
    billId: String = "",
    billName: String = "",
) {

    var secondTextFiledValue by rememberSaveable { mutableStateOf(billName) }

    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(Dimens.size_18)
            .clip(
                RoundedCornerShape(
                    topEnd = AppTheme.dimens.size_12,
                    topStart = AppTheme.dimens.size_12
                )
            )
    ) {

        if (logo.isNotEmpty()) {
            val painter = rememberAsyncImagePainter(
                model = logo,
                placeholder = painterResource(id = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature)
            )
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        vertical = AppTheme.dimens.size_8,
                    )
                    .size(42.dp)
            )
        }

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = false,
                value = billId,
                readonly = true,
                onValueChange = {},
                label = firstTextFiledTitle,
                labelStyle = AppTheme.typography.text_12PX_16SP_B,
                supportingText = error,

            )

            AppTextField(
                modifier = Modifier
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


@Preview(showBackground = true)
@ThemePreviews
@Composable
@DevicePreviews
internal fun PreviewEditeBillContent() {
    AppTheme {
        AppBackground(modifier = Modifier) {}

        EditeBillContent(
            secondTextFiledTitle = aboPayStringResource(id = R.string.title_optional),
            firstTextFiledTitle = aboPayStringResource(id = R.string.phone_number_with_pre),
            secondTextFiledPlaceHolder = aboPayStringResource(id = R.string.name_of_bill_place_holder),
            logo = "",
            modifier = Modifier,
            onValueChangeFirstTextFiled = {},
            onValueChangeSecondTextFiled = {},
            error = ""
        )
    }
}
