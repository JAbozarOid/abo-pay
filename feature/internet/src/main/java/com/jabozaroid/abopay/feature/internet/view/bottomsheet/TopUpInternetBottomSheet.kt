package com.jabozaroid.abopay.feature.internet.view.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.internet.model.SimType

//region TopUp Internet bottom sheet
@Composable
internal fun TopUpInternetBottomSheet(
    bottomSheetTitle: String,
    onHideBottomSheet: () -> Unit,
    onBtnConfirmClicked: (SimType) -> Unit,
    onBtnCancelClicked: () -> Unit,
    confirmBtnText: String, cancelBtnText: String,
    dualActionBtn: Boolean = true,
) {
    val radioOptions = listOf(SimType.PREPAID, SimType.POSTPAID)
    var selectedOption by remember { mutableStateOf(radioOptions[1]) }
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            TopUpInternetBottomSheetContent(
                radioOptions = radioOptions,
                selectedOption = selectedOption
            ) {
                selectedOption = it
            }
        },
        onDismiss = onHideBottomSheet,
        onBtn2Click = onBtnCancelClicked,
        onBtn1Click = { onBtnConfirmClicked(selectedOption) },
        btn1Text = confirmBtnText,
        btn2Text = cancelBtnText,
        dualActionBtn = dualActionBtn
    )

}
//endregion

@Composable
fun TopUpInternetBottomSheetContent(
    radioOptions: List<SimType>,
    selectedOption: SimType,
    onSelectedOptionClicked: (SimType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        radioOptions.forEach { internetType ->
            Row(
                modifier = Modifier.padding(horizontal = Dimens.size_5),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.spacedBy((-8).dp)
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = internetType.title,
                    color = AppTheme.colorScheme.ivaTitleText,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.text_9PX_12SP_M
                )
                RadioButton(
                    selected = (internetType == selectedOption),
                    onClick = { onSelectedOptionClicked(internetType) },
                    colors = RadioButtonColors(
                        selectedColor = AppTheme.colorScheme.primary,
                        unselectedColor = AppTheme.colorScheme.outline,
                        disabledSelectedColor = AppTheme.colorScheme.primary,
                        disabledUnselectedColor = AppTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@ThemePreviews
@Composable
//@DevicePreviews
internal fun PreviewTopUpInternetBottomSheetContent() {
    AppTheme() {
        val radioOptions = listOf(SimType.PREPAID, SimType.POSTPAID)
        val selectedOption by remember { mutableStateOf(radioOptions[1]) }
        TopUpInternetBottomSheetContent(
            radioOptions,
            selectedOption
        ) {

        }
    }
}