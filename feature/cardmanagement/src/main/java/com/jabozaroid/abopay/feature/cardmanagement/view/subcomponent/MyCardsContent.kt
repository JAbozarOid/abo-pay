package com.jabozaroid.abopay.feature.cardmanagement.view.subcomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.cardmanager.CardList
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.feature.cardmanagement.model.CardManagementUiModel

/**
 * Created on 24,November,2024
 */

@Composable
fun MyCardsContent(
    modifier: Modifier = Modifier,
    state: CardManagementUiModel,
    onCardSelected: (Card) -> Unit,
    onActionCopy: (androidx.compose.ui.platform.ClipboardManager, String) -> Unit,
    onDefaultCardSelected: () -> Unit = {},
    onDeleteCardSelected: () -> Unit = {},
    onEditCardSelected: () -> Unit = {},
) {
    Column(modifier = modifier) {
        CardList(
            list = state.userCardList,
            onCardSelected = {
                onCardSelected(it)
            }
        )
        MyCardsOptions(
            state = state,
            onActionCopy = onActionCopy,
            onDefaultCardSelected = onDefaultCardSelected,
            onDeleteCardSelected = onDeleteCardSelected,
            onEditCardSelected = onEditCardSelected
        )
    }
}


@Preview(showBackground = true)
@ThemePreviews
@DevicePreviews
@Composable
internal fun PreviewMyCardsContent() {
    AppTheme {
        MyCardsContent(state = CardManagementUiModel(),
            onCardSelected = {},
            onActionCopy = { _, _ -> })
    }
}