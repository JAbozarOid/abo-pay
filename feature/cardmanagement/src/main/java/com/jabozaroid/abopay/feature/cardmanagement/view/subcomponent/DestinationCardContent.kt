package com.jabozaroid.abopay.feature.cardmanagement.view.subcomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.ListContent
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementUiModel

/**
 *  Created on 11/26/2024 
 **/



@Composable
fun DestinationCardsContent(
    modifier: Modifier = Modifier,
    state: CardManagementUiModel,
    onDeleteIconClicked: (SearchItemModel) -> Unit,
    onEditIconClicked: (SearchItemModel) -> Unit,
) {
    if (state.destinationCards.isEmpty())
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                aboPayStringResource(id = R.string.no_destination_cards),
                style = AppTheme.typography.text_12PX_16SP_M,
                color = AppTheme.colorScheme.aboTitleText3,
                textAlign = TextAlign.Center
            )
        }
    else DestinationCardList(
        state,
        modifier,
        onDeleteIconClicked,
        onEditIconClicked,
    )
}

@Composable
fun DestinationCardList(
    state: CardManagementUiModel,
    modifier: Modifier,
    onDeleteIconClicked: (SearchItemModel) -> Unit,
    onEditIconClicked: (SearchItemModel) -> Unit,
) {

    ListContent(
        modifier = modifier,
        searchItems = state.destinationCards,
        leftIcon = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_delete,
        secondIcon = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_edit_item,
        onLeftIconClicked = {
            onDeleteIconClicked(it)
        },
        onSecondIconClicked = {
            onEditIconClicked(it)
        }
    )
}