package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 * Created on 30,November,2024
 */

@Composable
fun QuestionnaireBottomSheet(
    bottomSheetTitle: String,
    bottomSheetQuestion: String,
    onHideBottomSheet: () -> Unit = {},
    onBtnConfirmClicked: () -> Unit = {},
) {
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.size_40),
                text = bottomSheetQuestion,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.text_12PX_16SP_M.copy(
                    color = AppTheme.colorScheme.aboTitleText
                ),
            )
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        onBtn2Click = onHideBottomSheet
    )
}