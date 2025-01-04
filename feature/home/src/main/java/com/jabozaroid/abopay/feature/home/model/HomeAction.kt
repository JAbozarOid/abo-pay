package com.jabozaroid.abopay.feature.home.model

import com.jabozaroid.abopay.core.ui.model.IAction


sealed interface HomeAction : IAction {

    data object EntryButtonClicked : HomeAction
    data object EntryButtonWebView : HomeAction
    data object EntryButtonLogin : HomeAction

    data object NavigateToBill : HomeAction
    data object NavigateToCharge : HomeAction
    data object NavigateToInternet : HomeAction
    data object NavigateToCardToCard : HomeAction
    data object NavigateToBalance : HomeAction
    data object NavigateToCardManagement : HomeAction
    data object NavigateToKahroba : HomeAction

}