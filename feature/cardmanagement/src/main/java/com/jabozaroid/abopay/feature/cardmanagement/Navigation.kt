package com.jabozaroid.abopay.feature.cardmanagement

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.feature.cardmanagement.view.CardManagementScreen
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes


val cardManagementScreen = CardManagementScreen()

fun NavGraphBuilder.cardManagementGraph() {

    navigation(
        route = ApplicationRoutes.cardManagementGraphRoute,
        startDestination = ApplicationRoutes.cardManagementScreenRoute
    ) {

        registerDestination(cardManagementScreen)

    }

}