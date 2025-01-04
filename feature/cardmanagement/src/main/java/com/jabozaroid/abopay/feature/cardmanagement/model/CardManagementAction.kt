package com.jabozaroid.abopay.feature.cardmanagement.model

import androidx.compose.ui.platform.ClipboardManager
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.ui.model.IAction

/**
 * Created on 24,November,2024
 */

sealed interface CardManagementAction : IAction {
    data object NavigateUp : CardManagementAction
    data object OnDestinationCardDeleteClicked : CardManagementAction
    data class OnShowEditDestinationCardBottomSheet(val card: SearchItemModel) :
        CardManagementAction

    data object OnHideEditDestinationCardBottomSheet : CardManagementAction
    data class OnDestinationCardEditClicked(val item: SearchItemModel) : CardManagementAction
    data object OnShowAddCardBottomSheet : CardManagementAction
    data object OnHideAddCardBottomSheet : CardManagementAction
    data class OnUserCardAdded(val card: Card) : CardManagementAction
    data class OnUserCardNumberChanged(val cardNumber: String) : CardManagementAction
    data class OnUserCardMonthChanged(val cardMonth: String) : CardManagementAction
    data class OnUserCardYearChanged(val cardYear: String) : CardManagementAction
    data class OnUserCardOwnerNameChanged(val ownerName: String) : CardManagementAction
    data class OnCopyCardClicked(val clipboardManager: ClipboardManager, val cardNumber: String) :
        CardManagementAction
    data class OnUserCardSelected(val card: Card) : CardManagementAction
    data class OnDefaultCardStateChanged(val state: Boolean) : CardManagementAction
    data object OnShowDefaultCardBottomSheet : CardManagementAction
    data object OnHideDefaultCardBottomSheet : CardManagementAction
    data object OnDefaultCardSelectedBottomSheet : CardManagementAction
    data object OnShowDeleteCardBottomSheet : CardManagementAction
    data object OnHideDeleteCardBottomSheet : CardManagementAction
    data object OnDeleteCardSelectedBottomSheet : CardManagementAction
    data object OnShowEditCardBottomSheet : CardManagementAction
    data object OnHideEditCardBottomSheet : CardManagementAction
    data object OnEditCardSelectedBottomSheet : CardManagementAction
    data class OnEditCardNameChanged(val name: String) : CardManagementAction
    data class OnEditCardMonthChanged(val month: String) : CardManagementAction
    data class OnEditCardYearChanged(val year: String) : CardManagementAction
    data object OnShowAddDestinationCardBottomSheet : CardManagementAction
    data object OnHideAddDestinationCardBottomSheet : CardManagementAction
    data object OnAddDestinationCardClicked : CardManagementAction
    data class OnAddDestinationCardNumberChanged(val cardNumber: String) : CardManagementAction
    data class OnShowDeleteDestinationCardBottomSheet(val card: SearchItemModel) :
        CardManagementAction

    data object OnHideDeleteDestinationCardBottomSheet : CardManagementAction

}