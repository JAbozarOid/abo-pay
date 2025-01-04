package com.jabozaroid.abopay.feature.intro.viewmodel

import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.intro.model.IntroAction
import com.jabozaroid.abopay.feature.intro.model.IntroEvent
import com.jabozaroid.abopay.feature.intro.model.IntroUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class IntroViewModel @Inject constructor() :
    BaseViewModel<IntroUiModel, IntroAction, IntroEvent>(
        initialState = IntroUiModel()
    ) {

    override val onRefresh: () -> Unit
        get() = { }

    override fun handleAction(action: IntroAction) {
        when (action) {
            IntroAction.EntryButtonClicked -> {
                navigateToBill()
                //navigateToCharge()
            }
        }
    }

    private fun navigateToLogin() {
        navigateTo(
            NavigationCommand.ToScreen(ApplicationRoutes.homeScreenRoute)
        )
    }

    private fun navigateToBill() {
        navigateTo(
            NavigationCommand.ToScreen(ApplicationRoutes.billScreenRoute)
        )
    }

    private fun navigateToCharge() {
        navigateTo(
            NavigationCommand.ToScreen(ApplicationRoutes.chargeScreenRoute)
        )
    }
}