package com.jabozaroid.abopay.feature.webview

import android.annotation.SuppressLint
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationAnimation
import com.jabozaroid.abopay.feature.webview.view.WebViewScreen

// todo (abozar) : define DashboardScreen


@SuppressLint("StaticFieldLeak")
val webViewScreen = WebViewScreen()

fun NavGraphBuilder.webViewGraph() {

    navigation(
        route = ApplicationRoutes.webViewGraphRoute,
        startDestination = ApplicationRoutes.webViewScreenRoute
    ) {
        registerDestination(
            screen = webViewScreen,
            enterTransition = NavigationAnimation.slideInFromLeft,
            exitTransition = NavigationAnimation.slideOutFromRight
        )

    }

}

