package com.jabozaroid.abopay.core.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

typealias EnterTransitionProvider = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)

typealias ExitTransitionProvider = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)

object NavigationAnimation {

    val slideInFromLeft: EnterTransitionProvider = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(delayMillis = 0, durationMillis = 400)
        )
    }

    val slideInFromRight: EnterTransitionProvider = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(delayMillis = 0, durationMillis = 400)
        )
    }

    val slideOutFromRight: ExitTransitionProvider = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(delayMillis = 0, durationMillis = 400)
        )
    }

    val slideOutFromLeft: ExitTransitionProvider = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(delayMillis = 0, durationMillis = 400)
        )
    }

}