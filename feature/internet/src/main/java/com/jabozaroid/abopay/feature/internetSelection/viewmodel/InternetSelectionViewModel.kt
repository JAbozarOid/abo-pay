package com.jabozaroid.abopay.feature.internetSelection.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.model.internet.OperatorItem
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.internet.GetTopUpInternetUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.internet.extention.toIconItemUiModel
import com.jabozaroid.abopay.feature.internet.model.mapToInternetCatalogUIModel
import com.jabozaroid.abopay.feature.internet.preview.dataModel
import com.jabozaroid.abopay.feature.internet.viewmodel.InternetViewModel.Companion.TAG
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionAction
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionEvent
import com.jabozaroid.abopay.feature.internetSelection.model.InternetSelectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetSelectionViewModel @Inject constructor(
    private val getTopUpInternetUseCase: GetTopUpInternetUseCase,

    ) :
    BaseViewModel<InternetSelectionUiModel, InternetSelectionAction, InternetSelectionEvent>(
        initialState = InternetSelectionUiModel()
    ) {
    override val onRefresh: () -> Unit
        get() = { }


    override fun handleAction(action: InternetSelectionAction) {
        when (action) {
            is InternetSelectionAction.GetInternetList -> getInternetListByOperator(
                action.selectedOperator,
                action.simType
            )

            InternetSelectionAction.NavigateToPaymentScreenSuccessfully -> createPaymentModel()
            InternetSelectionAction.NavigateUp -> navigateBack()
        }
    }

    private fun createPaymentModel() {
        navigateTo(
            command = NavigationCommand.ToWithData(
                ApplicationRoutes.paymentConfirmationScreenRoute + ApplicationRoutes.paymentConfirmationParam,
                linkedMapOf(
                    Pair(
                        NavigationParam.PAYMENT_CONFIRMATION_MODEL,
                        Gson().toJson(dataModel)
                    )
                )
            )
        )
    }

    private fun getInternetListByOperator(operatorName: String, simType: String) {
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

                sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }
        ) {
            updateState {
                it.copy(loading = true)
            }

            getTopUpInternetUseCase.execute(null)

                .onAboPayException { throwable ->
                    Log.d(TAG, "getTopUpInternetCatalog: onError: ${throwable.message}")
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                    updateState {
                        it.copy(loading = false)
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getTopUpInternetCatalog: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                    updateState {
                        // todo : remove this mock line in production
                        it.copy(
                            loading = false
                        )
                    }
                }
                .onAboPaySuccess { response ->
                    Log.d(TAG, "getTopUpInternetCatalog: onSuccess ${response?.operators}")
                    response?.let { res ->
                        if (res.operators.isNotEmpty()) {
                            val operatorList = res.operators.toMutableList()
                            val indexedOperatorList: List<OperatorItem> =
                                operatorList.mapIndexed { index, operatorItem ->
                                    operatorItem.copy(index = index)
                                }.toList()

                            updateState {
                                it.copy(
                                    loading = false,
                                    operatorUiModels = indexedOperatorList.toIconItemUiModel()
                                )
                            }

                            val operatorData =
                                currentState.operatorUiModels.first { operator ->
                                    operator.title == operatorName
                                }
                            val logo = operatorData.lightLogo
                            val catalogList = operatorData.categories[0].services

                            updateState {
                                it.copy(
                                    loading = false,
                                    internetCatalogList = catalogList.mapToInternetCatalogUIModel(
                                        simType
                                    ),
                                )
                            }
                        }
                    }
                }
        }

    }
}