package com.jabozaroid.abopay.feature.intro

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.feature.intro.view.IntroScreen


val introScreen = IntroScreen()

fun NavGraphBuilder.introGraph() {

    navigation(
        route = ApplicationRoutes.introGraphRoute,
        startDestination = ApplicationRoutes.introScreenRoute
    ) {

        registerDestination(introScreen)

    }

}