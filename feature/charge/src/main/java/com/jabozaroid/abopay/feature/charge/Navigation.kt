package com.jabozaroid.abopay.feature.charge

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.charge.view.ChargeScreen


val chargeScreen = ChargeScreen()

fun NavGraphBuilder.chargeGraph() {

    navigation(
        route = ApplicationRoutes.chargeGraphRoute,
        startDestination = ApplicationRoutes.chargeScreenRoute
    ) {

        registerDestination(chargeScreen)

    }

}