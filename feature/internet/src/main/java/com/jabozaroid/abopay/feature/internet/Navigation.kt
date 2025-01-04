package com.jabozaroid.abopay.feature.internet

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.internet.view.InternetScreen
import com.jabozaroid.abopay.feature.internetSelection.view.InternetSelectionListScreen


val internetScreen = InternetScreen()
val internetSelectionListScreen = InternetSelectionListScreen()

fun NavGraphBuilder.internetGraph() {

    navigation(
        route = ApplicationRoutes.internetGraphRoute,
        startDestination = ApplicationRoutes.internetScreenRoute
    ) {

        registerDestination(internetScreen)
        registerDestination(internetSelectionListScreen)

    }

}