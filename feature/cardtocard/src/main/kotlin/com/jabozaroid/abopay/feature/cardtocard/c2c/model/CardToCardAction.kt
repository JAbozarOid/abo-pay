package com.jabozaroid.abopay.feature.cardtocard.c2c.model

import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.ui.model.IAction


/**
 *  Created on 9/29/2024 
 **/
sealed interface CardToCardAction : IAction {
    data class SetAmount(val amount: String) : CardToCardAction
    data class OnDescriptionValueChanged(val description: String) : CardToCardAction
    data class ShowTargetCardBottomSheet(val show : Boolean) : CardToCardAction
    data class SetTargetCardNumber(val cardNumber: String) : CardToCardAction
    data class OnSourceCardSelected(val card: Card) : CardToCardAction
    data class OnSaveStateChanged(val saveDestinationCard: Boolean) : CardToCardAction
    data object OnToolbarIconClicked : CardToCardAction
    data object OnConfirmButtonClicked : CardToCardAction

    data object OnShowAddCardBottomSheet : CardToCardAction
    data object OnHideAddCardBottomSheet : CardToCardAction
    data object OnUserCardBtnAddClicked : CardToCardAction
    data class OnUserCardAdded(val card: Card) : CardToCardAction


    data class OnUserCardNumberChanged(val cardNumber : String) : CardToCardAction

    data class OnUserCardMonthChanged(val cardMonth : String) : CardToCardAction
    data class OnUserCardYearChanged(val cardYear : String) : CardToCardAction
    data class OnUserCardOwnerNameChanged(val ownerName : String) : CardToCardAction
    data class OnCardItemDelete(val cardItem : SearchItemModel) : CardToCardAction
    data class OnDefaultCardStateChanged(val state: Boolean) : CardToCardAction
    data class OnClearCardClicked(val cardNumber: String, val cleared: (Boolean) -> Unit) :
        CardToCardAction

}