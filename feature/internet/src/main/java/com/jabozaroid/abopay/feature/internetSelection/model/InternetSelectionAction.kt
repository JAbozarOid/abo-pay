package com.jabozaroid.abopay.feature.internetSelection.model

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface InternetSelectionAction : IAction {
    data object NavigateUp : InternetSelectionAction
    data object NavigateToPaymentScreenSuccessfully : InternetSelectionAction
    data class GetInternetList(val selectedOperator: String, val simType: String) :
        InternetSelectionAction

}