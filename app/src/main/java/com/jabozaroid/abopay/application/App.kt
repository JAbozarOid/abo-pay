package com.jabozaroid.abopay.application

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.CustomSnackBar
import com.jabozaroid.abopay.core.designsystem.component.SnackState
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.toolbar.TopAppBar
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.domain.model.user.User
import com.jabozaroid.abopay.core.ui.helper.LocalNavController
import com.jabozaroid.abopay.core.ui.helper.LocalSnackBarState
import com.jabozaroid.abopay.navigation.AppNavHost
import com.jabozaroid.abopay.navigation.TopLevelDestination

@SuppressLint("UnrememberedMutableState")
@Composable
fun App(
    user: User? = null,
    startDestination: String,
    appState: AppState = rememberAboPayAppState(
        startDestination = startDestination,
        user = user
    )
) {
    // todo (atiye) : impl current destination


    //todo (abozar) : show hide bottom navigation base on user session status

    AppBackground(
        modifier = Modifier
    ) {
        val snackBarHostState = remember {
            SnackbarHostState()
        }

        Scaffold(
            modifier = Modifier,
            topBar = {},
            bottomBar = {
                //todo (abozar) : show navigation bar
            },
            containerColor = Color.Transparent,
            contentColor = AppTheme.colorScheme.onBackground,
            snackbarHost = {
                SnackbarHost(snackBarHostState) { snackbarData ->
                    CustomSnackBar(
                        message = snackbarData.visuals.message,
                        snackState = snackbarData.visuals.actionLabel?.let {
                            if (it == SnackState.MESSAGE.name)
                                SnackState.MESSAGE
                            else
                                SnackState.ERROR
                        } ?: SnackState.MESSAGE
                    )
                }
            },
        ) { padding ->

            CompositionLocalProvider(
                LocalSnackBarState provides snackBarHostState,
                LocalNavController provides appState.navController
            ) {

                AppNavHost(
                    startDestination = appState.startDestination,
                    modifier = Modifier.padding(padding),
                    appState = appState
                )

            }
        }
    }

}

// todo : impl navigation bar composable function


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(modifier: Modifier = Modifier, topLevelDestination: TopLevelDestination) {
    AppTheme {
        TopAppBar(
            modifier = modifier,
            title = topLevelDestination.title
        )
    }
}

@ThemePreviews
@Composable
fun PreviewToolbar() {
    AppTheme {
        //todo (abozar) : show toolbar in preview
    }
}

@ThemePreviews
@Composable
fun PreviewNavigationBar() {
    AppTheme {
        // todo (abozar) : show navigation bar in preview mode

    }
}
