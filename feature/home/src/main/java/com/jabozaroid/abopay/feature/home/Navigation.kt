package com.jabozaroid.abopay.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationAnimation
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.feature.home.view.HomeScreen
import com.jabozaroid.abopay.feature.home.view.SecondHomeScreen
import com.jabozaroid.abopay.feature.webview.view.WebViewScreen

// todo (abozar) : define DashboardScreen


val homeScreen = HomeScreen()
val secondHomeScreen = SecondHomeScreen()
val webViewScreen = WebViewScreen()

fun NavGraphBuilder.homeGraph() {

    navigation(
        route = ApplicationRoutes.homeGraphRoute,
        startDestination = ApplicationRoutes.homeScreenRoute
    ) {
        registerDestination(
            screen = homeScreen,
            enterTransition = NavigationAnimation.slideInFromLeft,
            exitTransition = NavigationAnimation.slideOutFromRight
        )
        registerDestination(
            secondHomeScreen,
            listOf(
                navArgument(NavigationParam.PHONE_NUMBER.name) { type = NavType.StringType }
            )
        )
        registerDestination(screen = webViewScreen)
    }

}

