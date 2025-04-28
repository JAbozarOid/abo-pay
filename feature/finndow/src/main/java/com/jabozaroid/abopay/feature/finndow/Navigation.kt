package com.jabozaroid.abopay.feature.finndow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.finndow.view.auth.FinndowAuthScreen
import com.jabozaroid.abopay.feature.finndow.view.home.FinndowHomeScreen
import com.jabozaroid.abopay.feature.finndow.view.shadowing.FinndowShadowingScreen


val finndowAuthScreen = FinndowAuthScreen()
val finndowHomeScreen = FinndowHomeScreen()
val finndowShadowingScreen = FinndowShadowingScreen()

fun NavGraphBuilder.finndowGraph() {

    navigation(
        route = ApplicationRoutes.FINNDOW_AUTH_GRAPH_ROUTE,
        startDestination = ApplicationRoutes.FINNDOW_AUTH_SCREEN_ROUTE
    ) {

        registerDestination(finndowAuthScreen)
        registerDestination(finndowHomeScreen)
        registerDestination(finndowShadowingScreen)
    }

}