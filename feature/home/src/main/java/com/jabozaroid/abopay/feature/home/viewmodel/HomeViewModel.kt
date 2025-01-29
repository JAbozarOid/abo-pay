package com.jabozaroid.abopay.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.home.GetHomeServiceUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.home.model.HomeAction
import com.jabozaroid.abopay.feature.home.model.HomeEvent
import com.jabozaroid.abopay.feature.home.model.HomeUiModel
import com.jabozaroid.abopay.feature.webview.model.HttpMethodType
import com.jabozaroid.abopay.feature.webview.model.WebViewUrlItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeServiceUseCase: GetHomeServiceUseCase,
) :
    BaseViewModel<HomeUiModel, HomeAction, HomeEvent>(
        initialState = HomeUiModel()
    ) {
    @Inject
    lateinit var sharedPrefStorage: SharedPrefStorage

    override val onRefresh: () -> Unit
        get() = {
            updateState {
                it.copy(
                    hasError = false,
                    aboPayException = AboPayExceptionMessage(),
                    aboPayApiError = AboPayServerError()
                )
            }
            getHomeServices()
        }

    init {
        getHomeServices()
    }


    override fun handleAction(action: HomeAction) {
        when (action) {
            HomeAction.EntryButtonClicked -> {
                navigateToSecondHomeScreenWithParams()
            }

            HomeAction.EntryButtonWebView -> {
                navigateToWebViewScreen()
            }

            HomeAction.EntryButtonLogin -> {
                navigateToLoginScreen()
            }

            HomeAction.NavigateToBill -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.billGraphRoute
                    )
                )
            }

            HomeAction.NavigateToCharge -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.chargeGraphRoute
                    )
                )
            }

            HomeAction.NavigateToInternet -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.internetGraphRoute
                    )
                )
            }

            HomeAction.NavigateToCardToCard -> navigateTo(
                NavigationCommand.ToScreen(
                    ApplicationRoutes.cardToCardGraphRoute
                )
            )

            HomeAction.NavigateToBalance -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.balanceGraphRoute
                    )
                )
            }

            HomeAction.NavigateToCardManagement -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.cardManagementGraphRoute
                    )
                )
            }

            HomeAction.NavigateToKahroba -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.messengerGraphRoute
                    )
                )
            }
        }
    }

    private fun navigateToWebViewScreen() {
        navigateTo(
            NavigationCommand.ToWithData(
                ApplicationRoutes.webViewScreenRoute +
                        ApplicationRoutes.webViewParam,
                linkedMapOf(Pair(NavigationParam.WEB_VIEW_ITEM, "someUrl"))
            )
        )
    }

    private fun getWebUrlItem(): WebViewUrlItem {
        return WebViewUrlItem(
            HttpMethodType.Get,
            getUrl = "https://sag.com",
            getBody = null
        )
    }

    private fun navigateToLoginScreen() {
        navigateTo(
            NavigationCommand.ToScreen(
                ApplicationRoutes.loginScreenRoute
            )
        )
    }

    private fun navigateToSecondHomeScreenWithParams() {
        navigateTo(
            NavigationCommand.ToWithData(
                ApplicationRoutes.secondHomeScreenRoute +
                        ApplicationRoutes.introToHomeParams,
                linkedMapOf(Pair(NavigationParam.PHONE_NUMBER, "09121234567"))
            )
        )
    }

    private fun getHomeServices() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                updateState {
                    it.copy(loading = false)
                }
                if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                    throwable.message?.let {
                        sendEvent(IEvent.ShowSnackMessage(it))
                    }
                        ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                    return@CoroutineExceptionHandler
                }
            }
        ) {
            updateState {
                it.copy(loading = true)
            }
            homeServiceUseCase.execute(Unit)
                .onAboPayException { error ->
                    Log.d("onError getServices", error.text)
                    sendEvent(IEvent.ShowSnackMessage(error.text))
                    updateState {
                        it.copy(
                            loading = false,
                            aboPayException = error
                        )
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        "onApiErrorHandler getServices",
                        "${apiError.error.code} - ${apiError.error.message}"
                    )
                    updateState {
                        it.copy(
                            loading = false,
                            hasError = true,
                            aboPayApiError = apiError
                        )
                    }
                }
                .onAboPaySuccess { result ->
                    Log.d("onSuccess getServices", result.toString())
                    updateState {
                        it.copy(
                            loading = false,
                            homeServices = result
                        )
                    }
                }
        }

    }

    fun handleNavigation(categoryId: Int, title: String) {
        when (Pair(categoryId, title)) {
            3 to "شارژ" -> {
                process(HomeAction.NavigateToCharge)
            }

            3 to "قبض" -> {
                process(HomeAction.NavigateToBill)
            }

            3 to "اینترنت" -> {
                process(HomeAction.NavigateToInternet)
            }

            3 to "کارت به کارت" -> {
                process(HomeAction.NavigateToCardToCard)
            }

            3 to "موجودی" -> {
                process(HomeAction.NavigateToBalance)
            }

            3 to "اینترنت" -> {
                process(HomeAction.NavigateToInternet)
            }

            2 to "مدیریت کارت" -> {
                process(HomeAction.NavigateToCardManagement)
            }


            else -> {
                if (title.equals("عوارض خروج")) {
                    navigateTo(
                        NavigationCommand.ToWithData(
                            ApplicationRoutes.webViewScreenRoute +
                                    ApplicationRoutes.webViewParam,
                            linkedMapOf(Pair(NavigationParam.WEB_VIEW_ITEM, "url"))
                        )
                    )
                }
            }
        }
    }

}