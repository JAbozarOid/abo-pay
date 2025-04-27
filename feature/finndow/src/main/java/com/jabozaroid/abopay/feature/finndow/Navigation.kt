package com.jabozaroid.abopay.feature.finndow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.finndow.view.auth.FinndowAuthScreen
import com.jabozaroid.abopay.feature.finndow.view.finndow.FinndowHomeScreen


val finndowScreen = FinndowHomeScreen()
val finndowAuthScreen = FinndowAuthScreen()

fun NavGraphBuilder.finndowGraph() {

    navigation(
        route = ApplicationRoutes.finndowAuthGraphRoute,
        startDestination = ApplicationRoutes.finndowAuthScreenRoute
    ) {

        registerDestination(finndowAuthScreen)
        registerDestination(finndowScreen)
    }

}