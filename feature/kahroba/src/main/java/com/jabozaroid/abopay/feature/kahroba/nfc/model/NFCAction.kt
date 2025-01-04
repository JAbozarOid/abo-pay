package com.jabozaroid.abopay.feature.kahroba.nfc.model

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface NFCAction : IAction {
    data object NavigateUp : NFCAction
    data object OnShowHelperBottomSheet : NFCAction
    data object OnHideHelperBottomSheet : NFCAction
}