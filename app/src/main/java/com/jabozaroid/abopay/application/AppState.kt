package com.jabozaroid.abopay.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jabozaroid.abopay.core.domain.model.user.User
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAboPayAppState(
    startDestination: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    user: User? = null
): AppState {
    return remember(
        coroutineScope,
        navController
    ) {
        AppState(
            startDestination = startDestination,
            navController = navController,
            coroutineScope = coroutineScope,
            user = user
        )
    }
}

//todo (abozar) : impl destination
class AppState(
    val startDestination: String,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val user: User? = null
) {

}