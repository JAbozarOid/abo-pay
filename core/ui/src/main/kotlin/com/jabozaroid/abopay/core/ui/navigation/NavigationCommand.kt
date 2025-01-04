package com.jabozaroid.abopay.core.ui.navigation

import androidx.navigation.NavController

sealed class NavigationCommand(
    val route: String? = null
) {
    abstract fun navigate(navController: NavController)

    class ToScreen(
        route: String,
        private val clearTo: String? = null,
        private val popBackStack: Boolean = false,
        private val clearBackStack: Boolean = false,
    ) : NavigationCommand(route) {
        override fun navigate(navController: NavController) {
            if (popBackStack)
                navController.popBackStack()
            route?.let {
                navController.navigate(it) {
                    clearTo?.let {
                        popUpTo(clearTo) {
                            inclusive = true
                        }
                    }
                    if (clearBackStack)
                        popUpTo(0)
                }
            }
        }
    }

    class ToWithData(
        route: String,
        private val param: LinkedHashMap<NavigationParam, String>,
        private val popBackStack: Boolean = false,
        private val clearBackStack: Boolean = false,
    ) : NavigationCommand(route) {
        override fun navigate(navController: NavController) {
            if (popBackStack)
                navController.popBackStack()
            route?.let {
                var destinationRoute = it
                param.forEach {
                    destinationRoute = destinationRoute.replace("{${it.key}}", it.value)
                }
                navController.navigate(destinationRoute) {
                    if (clearBackStack)
                        popUpTo(0)
                }
            }
        }
    }

    data object Back : NavigationCommand(null) {
        override fun navigate(navController: NavController) {
            navController.popBackStack()
        }

    }

}

