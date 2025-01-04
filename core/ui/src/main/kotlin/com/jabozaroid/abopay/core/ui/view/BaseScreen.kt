package com.jabozaroid.abopay.core.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.jabozaroid.abopay.core.designsystem.component.InternetConnectionComponent
import com.jabozaroid.abopay.core.designsystem.component.RefreshComponent
import com.jabozaroid.abopay.core.designsystem.component.ShowLoading
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_8
import com.jabozaroid.abopay.core.domain.AppError
import com.jabozaroid.abopay.core.ui.entity.DisplayedError
import com.jabozaroid.abopay.core.ui.helper.LocalNavController
import com.jabozaroid.abopay.core.ui.helper.LocalSnackBarState
import com.jabozaroid.abopay.core.ui.helper.getComposableState
import com.jabozaroid.abopay.core.ui.helper.showSnackbar
import com.jabozaroid.abopay.core.ui.helper.showToast
import com.jabozaroid.abopay.core.ui.model.IAction
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseScreen<State : IViewState, Action : IAction, Event : IEvent>(
    val route: String,
    val name: String
) {

    protected val parameters = linkedMapOf<NavigationParam, String>()

    @Composable
    abstract fun ViewModel(): BaseViewModel<State, Action, Event>

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    open fun Screen(navBackStackEntry: NavBackStackEntry?) {
        with(ViewModel()) {

            navBackStackEntry?.let {
                initParameters(navBackStackEntry)
            }

            val state = getComposableState()
            BaseScreenBehavior(this) {
                Box {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Content(state.value)
                    }
                    if (state.value.loading)
                        ShowLoading()

                    if (state.value.hasError) {
                        when (currentState.aboPayApiError.error.code) {
                            401 -> {
                                //TODO: user logout and delete token
                                navigateTo(
                                    NavigationCommand.ToScreen(
                                        com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.loginGraphRoute
                                    )
                                )
                            }

                            in (402..599), 400 -> {
                                var modifier: Modifier = Modifier
                                    .padding(
                                        top = TopAppBarDefaults.TopAppBarExpandedHeight + (size_8 * 3),
                                        bottom = size_8
                                    )
                                    .padding(horizontal = size_8)

                                if (route == com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.homeScreenRoute) {
                                    modifier = Modifier
                                }
                                val coroutineScope = rememberCoroutineScope()
                                RefreshComponent(
                                    modifier = modifier
                                ) {
                                    coroutineScope.launch {
                                        onRefreshState.emit(true)
                                    }
                                }
                            }
                        }
                    }

                    if (currentState.aboPayException.type == AppError.NetworkError && currentState.aboPayException.text.isNotEmpty()) {
                        val coroutineScope = rememberCoroutineScope()
                        var modifier: Modifier = Modifier
                            .padding(
                                top = TopAppBarDefaults.TopAppBarExpandedHeight + (size_8 * 3),
                                bottom = size_8
                            )
                            .padding(horizontal = size_8)

                        if (route == com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes.homeScreenRoute) {
                            modifier = Modifier
                        }
                        InternetConnectionComponent(
                            modifier = modifier,
                            message = currentState.aboPayException.text
                        ) {
                            coroutineScope.launch {
                                onRefreshState.emit(true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initParameters(navBackStackEntry: NavBackStackEntry) {
        navBackStackEntry.arguments?.let {
            it.keySet().forEach { key ->
                NavigationParam.getByName(key)?.let { param ->
                    parameters[param] = it.getString(key) ?: ""
                }
            }
        }
    }

    @Composable
    protected fun HandleEvent(
        onEvent: (IEvent) -> Unit
    ) {
        with(ViewModel()) {
            LaunchedEffect(key1 = Unit) {
                uiEventFlow.collect {
                    onEvent.invoke(it)
                }
            }
        }
    }


    @Composable
    abstract fun Content(state: State)


    /**
     * this composable must be called
     * in the chain normal application flow
     * because it needs "LocalSnackBarState.current"
     * @see com.example.mark2.helper.LocalSnackBarState
     * @throws IllegalStateException when the snackbarHostState
     * not provided in call site
     */
    @Composable
    private fun BaseScreenBehavior(
        viewModel: BaseViewModel<State, Action, Event>,
        snackbarHostState: SnackbarHostState = LocalSnackBarState.current,
        context: Context = LocalContext.current,
        feature: (@Composable () -> Unit)
    ) {
        NavigationHandler(
            viewModel = viewModel,
            navController = LocalNavController.current
        ) {
            ErrorHandler(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                context = context
            ) {
                SnackHandler(
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState,
                    context = context
                ) {
                    ToastHandler(
                        viewModel = viewModel,
                        context = context
                    ) {
                        feature.invoke()
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationHandler(
        viewModel: BaseViewModel<State, Action, Event>,
        navController: NavController,
        content: (@Composable () -> Unit)
    ) {
        LaunchedEffect(Unit) {
            viewModel.navigationFlow.collect {
                it.navigate(navController)
            }
        }
        content.invoke()
    }


    @Composable
    fun ErrorHandler(
        viewModel: BaseViewModel<*, out IAction, *>,
        snackbarHostState: SnackbarHostState,
        context: Context,
        content: (@Composable () -> Unit)
    ) {
        LaunchedEffect(Unit) {
            viewModel.errorFlow.collect {
                when (it) {
                    is DisplayedError.SnackBarError -> {
                        snackbarHostState.showSnackbar(displayedError = it)
                    }

                    is DisplayedError.ToastError -> {
                        context.showToast(it)
                    }
                }
            }
        }
        content.invoke()
    }

    @Composable
    fun SnackHandler(
        viewModel: BaseViewModel<*, out IAction, *>,
        snackbarHostState: SnackbarHostState,
        context: Context,
        content: (@Composable () -> Unit)
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEventFlow.collect {
                if (it is IEvent.ShowSnack) {
                    snackbarHostState.showSnackbar(
                        message = context.getString(it.message),
                        actionLabel = it.snackState.name,
                        duration = SnackbarDuration.Short
                    )
                }
                if (it is IEvent.ShowSnackMessage) {
                    snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.snackState.name,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
        content.invoke()
    }

    @Composable
    fun ToastHandler(
        viewModel: BaseViewModel<*, out IAction, *>,
        context: Context,
        content: (@Composable () -> Unit)
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEventFlow.collect {
                if (it is IEvent.ShowToast) {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        content.invoke()
    }

}