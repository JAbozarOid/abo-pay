package com.jabozaroid.abopay.feature.internet.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.internet.OperatorItem
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.charge.DeleteFavoriteMobileNumberUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetFavouriteChargeNumUseCase
import com.jabozaroid.abopay.core.domain.usecase.internet.GetTopUpInternetUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.internet.R
import com.jabozaroid.abopay.feature.internet.extention.toIconItemUiModel
import com.jabozaroid.abopay.feature.internet.model.InternetAction
import com.jabozaroid.abopay.feature.internet.model.InternetEvent
import com.jabozaroid.abopay.feature.internet.model.InternetUiModel
import com.jabozaroid.abopay.feature.internet.preview.frequentList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetViewModel @Inject constructor(
    private val getTopUpInternetUseCase: GetTopUpInternetUseCase,
    private val getFavouriteChargeNumUseCase: GetFavouriteChargeNumUseCase,
    private val deleteFavoriteMobileNumberUseCase: DeleteFavoriteMobileNumberUseCase,
) : BaseViewModel<InternetUiModel, InternetAction, InternetEvent>(
    initialState = InternetUiModel()
) {

    companion object {
        const val TAG = "InternetViewModel"
    }

    init {
        getFrequentMockList()
    }

    override val onRefresh: () -> Unit
        get() = {
            updateState {
                it.copy(hasError = false)
            }
            getTopUpInternetCatalog()
        }

    override fun handleAction(action: InternetAction) {
        when (action) {

            InternetAction.NavigateUp -> {
                navigateBack()
            }

            InternetAction.GetTopUpInternets -> {
                getTopUpInternetCatalog()
            }

            is InternetAction.DeleteUserPhoneNumber -> {
                deleteFavoriteMobileNumber(action.phoneNumber)
            }

            is InternetAction.UserPhoneNumberValue -> {
                checkPhoneNumberValidation(action.phoneNumber)
            }

            is InternetAction.PhoneNumberErrorStatus -> {
                checkPhoneNumberErrorStatus(action.phoneNumber)
            }

            is InternetAction.PhoneNumberErrorValue -> {
                checkPhoneNumberError(action.phoneNumber)
            }

            is InternetAction.ClearOperatorStatusByPhoneEmpty -> {
                updateState {
                    it.copy(operatorIndex = action.index)
                }
            }

            is InternetAction.FindOperatorByUserInput -> {
                operatorFinderByIndex(action.phoneNumber, action.operatorList)
            }

            is InternetAction.GetLatestPhoneNumberValue -> {
                if (action.phoneNumber.length < 4) {
                    updateState {
                        it.copy(
                            operatorIndex = null
                        )
                    }
                } else
                    checkPhoneNumberValidation(action.phoneNumber)
            }

            is InternetAction.SelectOperatorItem -> {
                updateState {
                    it.copy(operatorIndex = action.index)
                }
            }

            is InternetAction.FrequentRemoveIconClicked -> {
                updateState {
                    it.copy(
                        removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                            isRemoveBottomSheetVisible = true,
                            selectedFrequentItem = action.item
                        )
                    )
                }
            }

            InternetAction.HideRemoveBottomSheet -> {
                updateState {
                    it.copy(
                        removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                            isRemoveBottomSheetVisible = false
                        )
                    )
                }
            }

            is InternetAction.RemoveFrequentItem -> {
                removeFrequentItem(action.item)
            }

            is InternetAction.TopUpInternets -> {
                getTopUpItems(action.operatorIndex, action.operatorList)
            }

            InternetAction.HideTopUpInternetsBottomSheet -> {
                updateState {
                    it.copy(
                        topUpInternetBottomSheetUiModel = it.topUpInternetBottomSheetUiModel.copy(
                            isBottomSheetVisible = false,
                        )
                    )
                }
            }

//            is InternetAction.NavigateToPaymentScreenSuccessfully -> createPaymentModel()

            is InternetAction.NavigateToInternetListScreen -> {
                updateState {
                    it.copy(
                        mobileNumberUiModel = it.mobileNumberUiModel.copy(
                            value = it.getMobileNumberValue()
                        ),
                        topUpInternetBottomSheetUiModel = it.topUpInternetBottomSheetUiModel.copy(
                            isBottomSheetVisible = false
                        ),
                        selectedSimType = action.simType
                    )
                }

                navigateTo(
                    NavigationCommand.ToWithData(
                        ApplicationRoutes.InternetSelectionListRoute + ApplicationRoutes.internetParam,
                        linkedMapOf(
                            Pair(
                                NavigationParam.SELECTED_OPERATOR,
                                currentState.operatorUiModels.first {
                                    it.index == currentState.operatorIndex
                                }.title ?: ""
                            ),
                            Pair(
                                NavigationParam.SIM_TYPE,
                                currentState.selectedSimType.title
                            ),
                            Pair(
                                NavigationParam.OPERATOR_LOGO,
                                currentState.operatorUiModels.first {
                                    it.index == currentState.operatorIndex
                                }.lightLogo?.replace('/', '~') ?: ""
                            )
                        )
                    )
                )
            }

//            is InternetAction.GetInternetList -> {
//                getInternetListByOperator(action.selectedOperator, action.simType)
//            }
        }
    }

//    private fun createPaymentModel() {
//        navigateTo(
//            command = NavigationCommand.ToWithData(
//                ApplicationRoutes.paymentConfirmationScreenRoute + ApplicationRoutes.paymentConfirmationParam,
//                linkedMapOf(
//                    Pair(
//                        NavigationParam.PAYMENT_CONFIRMATION_MODEL,
//                        Gson().toJson(dataModel)
//                    )
//                )
//            )
//        )
//
//    }

    private fun getTopUpInternetCatalog() {
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
            getTopUpInternetUseCase.execute(null)
                .onAboPayException { throwable ->
                    Log.d(TAG, "getTopUpInternetCatalog: onError: ${throwable.message}")
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                    updateState {
                        it.copy(loading = false, hasError = false, aboPayException = throwable)
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getTopUpInternetCatalog: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    updateState {
                        it.copy(
                            loading = false, hasError = true, aboPayApiError = apiError
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

                            getUserInternetPhoneNumbers(res.operators.toIconItemUiModel())
                        }
                    }
                }
        }
    }

    private fun checkPhoneNumberValidation(phoneNumber: String) {
        if (ValidationUtil.mobile(phoneNumber) == ValidationState.VALID) {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        value = phoneNumber,
                        errorMessage = null,
                        errorStatus = false
                    )
                )
            }
        }


    }

    private fun deleteFavoriteMobileNumber(phoneNumber: String) {
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

                sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.snack_error_delete_number))
            }
        ) {
            updateState {
                it.copy(loading = true)
            }

            deleteFavoriteMobileNumberUseCase.execute(
                param = DeleteFavouriteChargeNumberParam(phoneNumber = phoneNumber)
            )
                .onAboPayException { throwable ->
                    Log.d(TAG, "deleteFavoriteMobileNumber: onError: ${throwable.message}")
                    updateState {
                        it.copy(loading = false)
                    }
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "deleteFavoriteMobileNumber: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                    updateState {
                        it.copy(
                            loading = false
                        )
                    }
                }
                .onAboPaySuccess { response ->
                    Log.d(TAG, "deleteFavoriteMobileNumber: onSuccess $response")
                    val newUserChargePhoneNumbers =
                        currentState.userInternetPhoneNumbers.toMutableList().filterNot {
                            it.phoneNumber == phoneNumber
                        }

                    if (response) {
                        updateState {
                            it.copy(
                                loading = false,
                                userInternetPhoneNumbers = newUserChargePhoneNumbers
                            )
                        }
                        sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.snack_success_delete_number))
                    }

                }
        }
    }

    private fun checkPhoneNumberError(phoneNumber: String) {
        if (ValidationUtil.mobile(phoneNumber) != ValidationState.VALID && ValidationUtil.mobile(
                phoneNumber
            ) != ValidationState.EMPTY
        ) {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_phone_number
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        errorMessage = null
                    )
                )
            }
        }
    }

    private fun checkPhoneNumberErrorStatus(phoneNumber: String) {
        if (ValidationUtil.mobile(phoneNumber) != ValidationState.VALID && ValidationUtil.mobile(
                phoneNumber
            ) != ValidationState.EMPTY
        ) {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        value = "",
                        errorStatus = true
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        errorStatus = false
                    )
                )
            }
        }
    }

    private fun operatorFinderByIndex(phoneNumber: String, operatorList: List<IconItemUiModel>) {
        for (item in operatorList) {
            for (code in item.categoryCodesLst) {
                if (phoneNumber.contains(code)) {
                    updateState {
                        it.copy(operatorIndex = item.index)
                    }
                }
            }
        }
    }



    private fun getUserInternetPhoneNumbers(operatorList: List<IconItemUiModel>) {

        viewModelScope.launch(

            CoroutineExceptionHandler { _, throwable ->
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
            getFavouriteChargeNumUseCase.execute(
                param = FavouriteChargeNumberParam(
                    currentPage = "0",
                    recordPerPage = "10"
                )
            )
                .onAboPayException { throwable ->
                    Log.d(TAG, "getUserInternetPhoneNumbers onError: ${throwable.message}")
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getUserInternetPhoneNumbers onApiErrorHandler: code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                }
                .onAboPaySuccess { response ->
                    Log.d(TAG, "getUserInternetPhoneNumbers: onSuccess ${response?.items}")
                    response?.let { res ->
                        if (res.items.isNotEmpty())
                            updateState {
                                it.copy(
                                    loading = false,
                                    userInternetPhoneNumbers = res.items.map { item ->
                                        item.copy(
                                            icon = operatorIconFinder(
                                                item.phoneNumber,
                                                operatorList
                                            )
                                        )
                                    }
                                )
                            }
                    }
                }
        }
    }

    private fun operatorIconFinder(phoneNumber: String, operatorList: List<IconItemUiModel>): Int {
        var icon = 0
        for (item in operatorList) {
            for (code in item.categoryCodesLst) {
                if (phoneNumber.contains(code)) {
                    when (item.title) {
                        "MCI" -> icon = R.drawable.ic_mci

                        "MtnIrancell" -> icon = R.drawable.ic_irancell

                        "Rightel" -> icon = R.drawable.ic_rightel

                        "Shatel" -> icon = R.drawable.ic_shatel
                    }
                }
            }
        }
        return icon
    }

    private fun getFrequentMockList() {
        viewModelScope.launch {

            delay(2000)

            updateState {
                it.copy(
                    frequentItems = frequentList
                )
            }
        }

    }

    private fun removeFrequentItem( item: FrequentUiModel) {
        val frequentList = currentState.frequentItems
        frequentList.remove(item)
        updateState {
            it.copy(
                frequentItems = frequentList, removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                    isRemoveBottomSheetVisible = false
                )
            )
        }
    }

    private fun getTopUpItems(operatorIndex: Int, operatorList: List<IconItemUiModel>) {
        for (operatorItem in operatorList) {
            if (operatorIndex == operatorItem.index)
                updateState {
                    it.copy(
                        topUpInternetBottomSheetUiModel = it.topUpInternetBottomSheetUiModel.copy(
                            isBottomSheetVisible = true
                        )
                    )
                }
        }
    }
//
//    private fun getInternetListByOperator(operatorName: String, simType: String) {
//        viewModelScope.launch(
//            CoroutineExceptionHandler { _, throwable ->
//                updateState {
//                    it.copy(loading = false)
//                }
//
//                if (throwable is DisplayException) {
//                    throwable.message?.let {
//                        sendEvent(IEvent.ShowSnackMessage(it))
//                    }
//                        ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
//                    return@CoroutineExceptionHandler
//                }
//
//                sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
//            }
//        ) {
//            updateState {
//                it.copy(loading = true)
//            }
//
//            getTopUpInternetUseCase.execute(null)
//
//                .onEvaException { throwable ->
//                    Log.d(TAG, "getTopUpInternetCatalog: onError: ${throwable.message}")
//                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
//                    updateState {
//                        it.copy(loading = false)
//                    }
//                }
//                .onEvaApiError { apiError ->
//                    Log.d(
//                        TAG,
//                        "getTopUpInternetCatalog: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
//                    )
//                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
//                    updateState {
//                        // todo : remove this mock line in production
//                        it.copy(
//                            loading = false
//                        )
//                    }
//                }
//                .onEvaSuccess { response ->
//                    Log.d(TAG, "getTopUpInternetCatalog: onSuccess ${response?.operators}")
//                    response?.let { res ->
//                        if (res.operators.isNotEmpty()) {
//                            val operatorList = res.operators.toMutableList()
//                            val indexedOperatorList: List<OperatorItem> =
//                                operatorList.mapIndexed { index, operatorItem ->
//                                    operatorItem.copy(index = index)
//                                }.toList()
//
//                            updateState {
//                                it.copy(
//                                    loading = false,
//                                    operatorUiModels = indexedOperatorList.toIconItemUiModel()
//                                )
//                            }
//
//                            val operatorData =
//                                currentState.operatorUiModels.first { operator ->
//                                    operator.title == operatorName
//                                }
//                            val logo = operatorData.lightLogo
//                            val catalogList = operatorData.categories[0].services
//
//                            updateState {
//                                it.copy(
//                                    loading = false,
//                                    internetCatalogList = catalogList.mapToInternetCatalogUIModel(
//                                        simType
//                                    ),
//                                    selectedOperatorLogo = logo ?: ""
//                                )
//                            }
//                        }
//                    }
//                }
//        }
//
//    }

}