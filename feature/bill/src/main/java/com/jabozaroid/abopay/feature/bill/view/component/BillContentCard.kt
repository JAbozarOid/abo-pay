package com.jabozaroid.abopay.feature.bill.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.FrequentManagerComponent
import com.jabozaroid.abopay.core.designsystem.component.SelectableGridComponent
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.bill.model.BillUiModel

@Composable
internal fun ContentCard(
    modifier: Modifier,
    state: BillUiModel,
    onItemClick: (iconItem: IconItemUiModel) -> Unit,
    isSelectedItem: Boolean,
    onFrequentRemoveIconClicked: (item: FrequentUiModel) -> Unit,
    onFrequentEditIconClicked: (item: FrequentUiModel) -> Unit,
    onFrequentItemClick: (FrequentUiModel) -> Unit,
    onShowPaymentInputBottomSheet: () -> Unit,
    isOverlayViewVisibility: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = Dimens.size_4,
                start = Dimens.size_8,
                end = Dimens.size_8,
                bottom = Dimens.size_8,
            )
            .clip(RoundedCornerShape(Dimens.size_12))
            .background(AppTheme.colorScheme.background)
    ) {

        SelectableGridComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.size_20),
            items = state.billTypeItems,
            onItemClicked = onItemClick,
            isSelected = isSelectedItem
        )

        FrequentManagerComponent(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(Dimens.size_8),
            frequentItems = state.frequentBills,
            onFrequentRemoveIconClicked = onFrequentRemoveIconClicked,
            onFrequentEditIconClicked = onFrequentEditIconClicked,
            title = aboPayStringResource(R.string.frequent_bill),
            description = aboPayStringResource(id = R.string.bill_description)
        )

        ButtonsBox(onClickPaymentWithId = {
            onShowPaymentInputBottomSheet()
        },
            onClickScanId = {})
    }
}