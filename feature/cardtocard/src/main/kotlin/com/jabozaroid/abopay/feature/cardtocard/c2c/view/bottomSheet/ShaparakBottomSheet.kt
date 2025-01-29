package com.jabozaroid.abopay.feature.cardtocard.c2c.view.bottomSheet

import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.feature.cardtocard.c2c.model.CardToCardUiModel

//region authorization bottom sheet
@Composable
fun ShaparakBottomSheet(
    state: CardToCardUiModel,
    bottomSheetTitle: String,
    onHideBottomSheet: () -> Unit,
    onBtnConfirmClicked: () -> Unit,
    onBtnCancelClicked: () -> Unit,
    confirmBtnText: String,
    cancelBtnText: String
) {
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            ProvideTextStyle(value = AppTheme.typography.text_11PX_15SP_M) {
                Text(
                    text = aboPayStringResource(id = R.string.shaparak_rules_alert),
                    color = AppTheme.colorScheme.aboOutlineButtonText,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                )
            }
        },
        onDismiss = onHideBottomSheet,
        onBtn1Click = onBtnConfirmClicked,
        onBtn2Click = onBtnCancelClicked,
        btn1Text = confirmBtnText,
        btn2Text = cancelBtnText,
        isBtn1Enabled = true,
        dualActionBtn = true
    )
}
//endregion

@Preview
@Composable
@ThemePreviews
fun Preview() {

    AppTheme {
        AppBackground(modifier = Modifier) {}

        /*ShowAuthorizationBottomSheet(
            state = CardToCardUiModel(),
            bottomSheetTitle = "احراز هویت کارت",
            onHideBottomSheet = {},
            onBtnConfirmClicked = {},
            onBtnCancelClicked = {},
            confirmBtnText = "ادامه",
            cancelBtnText = "انصراف"
        )*/
    }
}
