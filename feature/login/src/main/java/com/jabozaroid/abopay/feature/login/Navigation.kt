package com.jabozaroid.abopay.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jabozaroid.abopay.core.ui.helper.registerDestination
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.feature.login.view.LoginScreen
import com.jabozaroid.abopay.feature.login.view.VerifyOtpScreen


val loginScreen  = LoginScreen()
val verifyOtpScreen  = VerifyOtpScreen()

fun NavGraphBuilder.loginGraph() {

    navigation(
        route = ApplicationRoutes.loginGraphRoute,
        startDestination = ApplicationRoutes.loginScreenRoute
    ) {

        registerDestination(loginScreen)
        registerDestination(
            verifyOtpScreen ,
            listOf(navArgument(NavigationParam.MOBILE.name){
                type = NavType.StringType
            })
        )

    }

}