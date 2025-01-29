package com.jabozaroid.abopay.feature.messenger.view.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerAction
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerEvent
import com.jabozaroid.abopay.feature.messenger.model.home.HomeMessengerUiModel
import com.jabozaroid.abopay.feature.messenger.viewmodel.HomeMessengerViewModel

class HomeMessengerScreen :
    BaseScreen<HomeMessengerUiModel, HomeMessengerAction, HomeMessengerEvent>(
        route = ApplicationRoutes.homeMessengerRoute, name = "HomeMessengerScreen"
    ) {

    @Composable
    override fun ViewModel(): HomeMessengerViewModel = hiltViewModel()

    @Composable
    override fun Content(state: HomeMessengerUiModel) {

    }
}