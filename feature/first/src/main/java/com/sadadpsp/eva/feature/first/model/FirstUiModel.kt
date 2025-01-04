package com.jabozaroid.abopay.feature.first.model

import com.jabozaroid.abopay.core.ui.model.IViewState

data class FirstUiModel(
    val loading: Boolean = false,
    val editTextModel: EditTextModel = EditTextModel(),
):IViewState {
    fun isBtnEnable(): Boolean {
        return editTextModel.error.isNullOrEmpty() && editTextModel.value.isEmpty()
    }
}

data class EditTextModel(
    val value: String = "",
    val error: String? = null
)
