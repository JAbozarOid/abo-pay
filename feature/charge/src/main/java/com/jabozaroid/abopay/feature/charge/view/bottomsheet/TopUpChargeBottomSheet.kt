package com.jabozaroid.abopay.feature.charge.view.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AmountGridComponent
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.component.SwitchComponent
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.charge.model.ChargeUiModel

//region TopUp charges bottom sheet
@Composable
fun TopUpChargesBottomSheet(
    state: ChargeUiModel, bottomSheetTitle: String, onHideBottomSheet: () -> Unit,
    onBtnConfirmClicked: () -> Unit, onBtnCancelClicked: () -> Unit,
    confirmBtnText: String, cancelBtnText: String,
    onSelectChargeAmount: (AmountUiModel) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    BottomSheetComponent(
        title = bottomSheetTitle,
        content = {
            SwitchComponent(
                modifier = Modifier.padding(
                    horizontal = Dimens.size_18,
                    vertical = Dimens.size_5
                ),
                titles = listOf(
                    aboPayStringResource(id = R.string.regular_charge),
                    aboPayStringResource(id = R.string.wonderful_charge)
                ),
                onTitleSelected = { index ->
                    selectedIndex = index
                },
            )
            AmountGridComponent(
                modifier = Modifier.padding(
                    horizontal = Dimens.size_18,
                ),
                items = if (selectedIndex == 0)
                    state.topUpChargesBottomSheetUiModel.topUpAmounts.filterNot { amountUiModel ->
                        amountUiModel.isWonderful
                    }
                else
                    state.topUpChargesBottomSheetUiModel.topUpAmounts.filter { amountUiModel ->
                        amountUiModel.isWonderful
                    },
                onItemClick = {
                    onSelectChargeAmount(it)
                })
            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.topUpChargesBottomSheetUiModel.errorMessage.aboPayStringResource()
                        ?: "",
                    color = AppTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )

            }
        },
        onDismiss = onHideBottomSheet,
        onBtn2Click = onBtnCancelClicked,
        onBtn1Click = onBtnConfirmClicked,
        btn1Text = confirmBtnText,
        btn2Text = cancelBtnText,
        isBtn1Enabled = state.topUpChargesBottomSheetUiModel.isChargeAmountSelect
    )

}
//endregion