package com.jabozaroid.abopay.feature.bill

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.feature.bill.view.BillScreen
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes

val billScreen = BillScreen()

fun NavGraphBuilder.billGraph() {
    navigation(
        route = ApplicationRoutes.billGraphRoute,
        startDestination = ApplicationRoutes.billScreenRoute
    ) {
        registerDestination(billScreen)
    }
}