package com.jabozaroid.abopay.feature.balance

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.balance.view.BalanceScreen

val balanceScreen = BalanceScreen()

fun NavGraphBuilder.balanceGraph() {
    navigation(
        route = ApplicationRoutes.balanceGraphRoute,
        startDestination = ApplicationRoutes.balanceScreenRoute
    ) {
        registerDestination(balanceScreen)
    }
}