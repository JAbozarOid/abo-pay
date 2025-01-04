package com.jabozaroid.abopay.feature.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.home.model.HomeAction
import com.jabozaroid.abopay.feature.home.model.HomeEvent
import com.jabozaroid.abopay.feature.home.model.HomeUiModel
import com.jabozaroid.abopay.feature.home.viewmodel.HomeViewModel


class SecondHomeScreen : BaseScreen<HomeUiModel, HomeAction, HomeEvent>(
    name = "SecondHome",
    route = ApplicationRoutes.secondHomeScreenRoute + ApplicationRoutes.introToHomeParams
) {
    @Composable
    override fun ViewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(state: HomeUiModel) {
        Column {
            Text(text = "Second Home screen: ${parameters[NavigationParam.PHONE_NUMBER]}")
        }
    }


}