package com.jabozaroid.abopay.feature.finndow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.finndow.view.FinndowHomeScreen


val finndowScreen = FinndowHomeScreen()

fun NavGraphBuilder.finndowGraph() {

    navigation(
        route = ApplicationRoutes.finndowHomeGraphRoute,
        startDestination = ApplicationRoutes.finndowHomeScreenRoute
    ) {

        registerDestination(finndowScreen)

    }

}