package com.jabozaroid.abopay.feature.first.model

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface FirstAction : IAction {

    data object btnClicked : FirstAction

}