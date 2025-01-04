package com.jabozaroid.abopay.feature.kahroba

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.kahroba.auth.view.KahrobaAuthScreen
import com.jabozaroid.abopay.feature.kahroba.main.view.KahrobaScreen
import com.jabozaroid.abopay.feature.kahroba.nfc.view.NFCScreen

val kahrobaAuthScreen = KahrobaAuthScreen()
val kahrobaScreen = KahrobaScreen()
val nfcScreen = NFCScreen()

fun NavGraphBuilder.kahrobaGraph() {
    navigation(
        route = ApplicationRoutes.kahrobaGraphRoute,
        startDestination = ApplicationRoutes.kahrobaAuthScreenRoute
    ) {
        registerDestination(kahrobaAuthScreen)
        registerDestination(kahrobaScreen)
        registerDestination(nfcScreen)
    }
}