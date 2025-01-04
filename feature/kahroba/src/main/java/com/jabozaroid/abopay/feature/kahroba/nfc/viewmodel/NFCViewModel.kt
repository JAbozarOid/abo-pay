package com.jabozaroid.abopay.feature.kahroba.nfc.viewmodel

import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.kahroba.main.viewmodel.helperData
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCAction
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCEvent
import com.jabozaroid.abopay.feature.kahroba.nfc.model.NFCUiModel
import javax.inject.Inject

class NFCViewModel @Inject constructor() : BaseViewModel<NFCUiModel, NFCAction, NFCEvent>(
    initialState = NFCUiModel()
) {
    override val onRefresh: () -> Unit
        get() = {}


    private fun helperBottomSheetVisibility(show: Boolean) {
        updateState {
            it.copy(
                helperBottomSheet = it.helperBottomSheet.copy(
                    isVisible = show,
                    items = helperData,
                    errorMessage = ""
                )
            )
        }
    }

    override fun handleAction(action: NFCAction) {
        when (action) {
            NFCAction.NavigateUp -> navigateBack()
            NFCAction.OnHideHelperBottomSheet -> helperBottomSheetVisibility(false)
            NFCAction.OnShowHelperBottomSheet -> helperBottomSheetVisibility(true)
        }
    }




}