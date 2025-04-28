package com.jabozaroid.abopay.feature.finndow.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.finndow.GetShadowingPracticeUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeAction
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeEvent
import com.jabozaroid.abopay.feature.finndow.model.home.FinndowHomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinndowHomeViewModel @Inject constructor(
    private val getShadowingPracticeUseCase: GetShadowingPracticeUseCase,
) :
    BaseViewModel<FinndowHomeUiModel, FinndowHomeAction, FinndowHomeEvent>(
        initialState = FinndowHomeUiModel()
    ) {

    companion object {
        const val TAG = "FinndowViewModel"
    }

    override val onRefresh: () -> Unit = {}

    override fun handleAction(action: FinndowHomeAction) {
        when (action) {
            FinndowHomeAction.OnRequestShadowingPractice -> {
                requestGetShadowingPractice()
            }

            FinndowHomeAction.OnShadowingPracticeBtnClicked -> {
                navigateToShadowingPracticeScreen()
            }
        }
    }

    private fun requestGetShadowingPractice() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                updateState {
                    it.copy(loading = false, hasError = true)
                }

                if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                    throwable.message?.let {
                        sendEvent(IEvent.ShowSnackMessage(it))
                    }
                        ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                    return@CoroutineExceptionHandler
                }

                sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }
        ) {
            updateState {
                it.copy(loading = true)
            }

            getShadowingPracticeUseCase.execute(
                ShadowingPracticeParam(
                    level = "beginner",
                    type = "pronunciation"
                )
            )
                .onAboPayException { throwable ->
                    Log.d(TAG, "getShadowingPracticeUseCase: onError: ${throwable.text}")
                    updateState {
                        it.copy(loading = false, aboPayException = throwable)
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getShadowingPracticeUseCase: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                    updateState {
                        it.copy(
                            loading = false, hasError = true,
                            aboPayApiError = apiError
                        )
                    }
                }
                .onAboPaySuccess { result ->
                    Log.d(TAG, "getShadowingPracticeUseCase: onSuccess $result")
                }

        }
    }

    private fun navigateToShadowingPracticeScreen() {
        navigateTo(NavigationCommand.ToScreen(route = ApplicationRoutes.FINNDOW_SHADOWING_SCREEN_ROUTE))
    }

}