package com.jabozaroid.abopay.feature.messenger

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.messenger.view.home.MessengerHomeScreen

val messengerHomeScreen = MessengerHomeScreen()

fun NavGraphBuilder.messengerGraph() {
    navigation(
        route = ApplicationRoutes.messengerGraphRoute,
        startDestination = ApplicationRoutes.messengerScreenRoute
    ) {

        registerDestination(messengerHomeScreen)

    }
}