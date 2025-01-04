package com.jabozaroid.abopay.feature.charge.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.CategoryItem
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.ServiceItem
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.FavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.OperatorItem
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.charge.DeleteFavoriteMobileNumberUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetFavouriteChargeNumUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetPinChargesUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetTopUpChargesUseCase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.charge.R
import com.jabozaroid.abopay.feature.charge.model.ChargeAction
import com.jabozaroid.abopay.feature.charge.model.ChargeEvent
import com.jabozaroid.abopay.feature.charge.model.ChargeUiModel
import com.jabozaroid.abopay.feature.charge.preview.dataModel
import com.jabozaroid.abopay.feature.charge.preview.frequentList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(
    private val getTopUpChargesUseCase: GetTopUpChargesUseCase,
    private val getPinChargesUseCase: GetPinChargesUseCase,
    private val getFavouriteChargeNumUseCase: GetFavouriteChargeNumUseCase,
    private val deleteFavoriteMobileNumberUseCase: DeleteFavoriteMobileNumberUseCase,
) : BaseViewModel<ChargeUiModel, ChargeAction, ChargeEvent>(
    initialState = ChargeUiModel()
) {

    companion object {
        const val TAG = "ChargeViewModel"
    }

    init {
        getFrequentMockList()
    }

    override val onRefresh: () -> Unit
        get() = {
            updateState {
                it.copy(hasError = false)
            }
            getTopUpChargeCatalog()
        }


    //region handleAction
    override fun handleAction(action: ChargeAction) {
        when (action) {

            ChargeAction.GetTopUpCharges -> {
                getTopUpChargeCatalog()
            }

            is ChargeAction.ClearOperatorStatusByPhoneEmpty -> {
                updateState {
                    it.copy(operatorIndex = action.index)
                }
            }

            is ChargeAction.UserPhoneNumberValue -> {
                checkPhoneNumberValidation(action.phoneNumber)

            }

            is ChargeAction.DeleteUserPhoneNumber -> {
                deleteFavoriteMobileNumber(action.phoneNumber)
            }

            // this method check error message
            is ChargeAction.PhoneNumberErrorValue -> {
                checkPhoneNumberError(action.phoneNumber)
            }

            // this method check error status
            is ChargeAction.PhoneNumberErrorStatus -> {
                checkPhoneNumberErrorStatus(action.phoneNumber)
            }

            is ChargeAction.FindOperatorByUserInput -> {
                operatorFinderByIndex(action.phoneNumber, action.operatorList)
            }

            is ChargeAction.TopUpCharges -> {
                getTopUpItems(action.operatorIndex, action.operatorList)
            }

            is ChargeAction.GetLatestPhoneNumberValue -> {
                if (action.phoneNumber.length < 4) {
                    updateState {
                        it.copy(
                            operatorIndex = null
                        )
                    }
                } else
                    checkPhoneNumberValidation(action.phoneNumber)
            }

            is ChargeAction.HideTopUpChargesBottomSheet -> {
                updateState {
                    it.copy(
                        topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                            isBottomSheetVisible = false,
                            errorMessage = null,
                            isChargeAmountSelect = false
                        )
                    )
                }
            }

            is ChargeAction.NavigateToPaymentScreenSuccessfully -> createPaymentModel()

            is ChargeAction.NavigateToPaymentScreenFailing -> {
                updateState {
                    it.copy(
                        topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                            isChargeAmountSelect = false,
                            errorMessage = com.jabozaroid.abopay.core.common.R.string.select_charge_alert
                        )
                    )
                }
            }

            is ChargeAction.SelectChargeAmount -> {
                checkSelectedChargeValidation(action.amountUiModel)
            }

            is ChargeAction.NavigateUp -> {
                navigateBack()
            }

            is ChargeAction.SelectOperatorItem -> {
                updateState {
                    it.copy(operatorIndex = action.index)
                }
            }

            is ChargeAction.FrequentRemoveIconClicked -> {
                updateState {
                    it.copy(
                        removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                            isRemoveBottomSheetVisible = true,
                            selectedFrequentItem = action.item
                        )
                    )
                }
            }

            is ChargeAction.HideRemoveBottomSheet -> {
                updateState {
                    it.copy(
                        removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                            isRemoveBottomSheetVisible = false
                        )
                    )
                }
            }

            is ChargeAction.RemoveFrequentItem -> {
                removeFrequentItem(action.item)
            }
        }
    }

    private fun createPaymentModel() {
        updateState {
            it.copy(
                topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                    isChargeAmountSelect = false,
                    errorMessage = null,
                    isBottomSheetVisible = false
                )
            )
        }

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
    //endregion

    private fun removeFrequentItem(item: FrequentUiModel) {
        val frequentList = currentState.frequentItems
        frequentList.remove(item)
        updateState {
            it.copy(
                frequentItems = frequentList,
                removeBottomSheetUiModel = it.removeBottomSheetUiModel.copy(
                    isRemoveBottomSheetVisible = false
                )
            )
        }
    }

    //region api call
    private fun getUserChargePhoneNumbers(operatorList: List<IconItemUiModel>) {

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
                    Log.d(TAG, "getUserChargePhoneNumbers onError: ${throwable.message}")
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getUserChargePhoneNumbers onApiErrorHandler: code= ${apiError.error.code} message= ${apiError.error.message}"
                    )
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                }
                .onAboPaySuccess { result ->
                    Log.d(TAG, "getUserChargePhoneNumbers: onSuccess ${result?.items}")
                    result?.let { res ->
                        if (res.items.isNotEmpty())
                            updateState {
                                it.copy(
                                    userChargePhoneNumbers = res.items.map { item ->
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

    // get topup charge catalog id =2
    fun getTopUpChargeCatalog() {
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

            getTopUpChargesUseCase.execute(null)
                .onAboPayException { throwable ->
                    Log.d(TAG, "getTopUpChargeCatalog: onError: ${throwable.text}")
                    updateState {
                        it.copy(loading = false, aboPayException = throwable)
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "getTopUpChargeCatalog: onApiErrorHandler : code= ${apiError.error.code} message= ${apiError.error.message}"
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
                    Log.d(TAG, "getTopUpChargeCatalog: onSuccess ${result?.operators}")
                    result?.let { res ->
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

                            getUserChargePhoneNumbers(res.operators.toIconItemUiModel())
                        }
                    }
                }
        }
    }
    //endregion

    fun deleteFavoriteMobileNumber(phoneNumber: String) {
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

                    updateState {
                        it.copy(
                            loading = false
                        )
                    }
                    sendEvent(IEvent.ShowSnackMessage("${apiError.error.code} message= ${apiError.error.message}"))
                }
                .onAboPaySuccess { result ->
                    Log.d(TAG, "deleteFavoriteMobileNumber: onSuccess $result")

                    val newUserChargePhoneNumbers =
                        currentState.userChargePhoneNumbers.toMutableList().filterNot {
                            it.phoneNumber == phoneNumber
                        }

                    if (result) {
                        updateState {
                            it.copy(
                                loading = false,
                                userChargePhoneNumbers = newUserChargePhoneNumbers
                            )
                        }
                        sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.snack_success_delete_number))
                    }

                }
        }
    }

    //region operator mock list

    private fun List<OperatorItem>.toIconItemUiModel(): List<IconItemUiModel> {
        val mapList: MutableList<IconItemUiModel> = mutableListOf()
        for ((index, item) in this.withIndex()) {
            mapList.add(
                index,
                IconItemUiModel(
                    darkLogo = item.darkLogoUrl,
                    lightLogo = item.lightLogoUrl,
                    title = item.name,
                    index = item.index,
                    categoryCodesLst = item.categoryCodesLst,
                    categories = item.categories.map {
                        it.toCategoryItemUiModel()
                    }
                )
            )
        }
        return mapList
    }
    //endregion

    //region validation
    fun checkPhoneNumberErrorStatus(phoneNumber: String) {
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

    fun checkPhoneNumberError(phoneNumber: String) {
        if (ValidationUtil.mobile(phoneNumber) != ValidationState.VALID && ValidationUtil.mobile(
                phoneNumber
            ) != ValidationState.EMPTY
        ) {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_phone_number,
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    mobileNumberUiModel = it.mobileNumberUiModel.copy(
                        errorMessage = null,

                        )
                )
            }
        }


    }

    fun checkPhoneNumberValidation(phoneNumber: String) {
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

    private fun checkSelectedChargeValidation(amountUiModel: AmountUiModel) {
        if (amountUiModel.amount.isEmpty()) {
            updateState {
                it.copy(
                    topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                        isChargeAmountSelect = false,
                        errorMessage = com.jabozaroid.abopay.core.common.R.string.select_charge_alert,
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                        isChargeAmountSelect = true,
                        errorMessage = null,
                    )
                )
            }
        }
    }
    //endregion

    //region operator finder by index
    fun operatorFinderByIndex(phoneNumber: String, operatorList: List<IconItemUiModel>) {
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
    //endregion

    //region TopUp items
    private fun getTopUpItems(operatorIndex: Int, operatorList: List<IconItemUiModel>) {
        for (operatorItem in operatorList) {
            if (operatorIndex == operatorItem.index)
                updateState {
                    it.copy(
                        topUpChargesBottomSheetUiModel = it.topUpChargesBottomSheetUiModel.copy(
                            topUpAmounts = operatorItem.categories[0].services.toAmountUiModel(),
                            isBottomSheetVisible = true
                        )
                    )
                }
        }
    }


    private fun List<ServiceItem>.toAmountUiModel(): List<AmountUiModel> {
        val amountList: MutableList<AmountUiModel> = mutableListOf()
        forEach {
            it.name?.let { name ->
                it.isWonderful?.let { isWonderful ->
                    amountList.add(AmountUiModel(amount = name, isWonderful = isWonderful))
                }
            }
        }
        return amountList
    }

    //endregion

    //region frequent charge items
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
    //endregion

    private fun com.jabozaroid.abopay.core.domain.model.charge.result.topup.CategoryItem.toCategoryItemUiModel(): CategoryItem =
        CategoryItem(
            code = this.code,
            name = this.name,
            services = this.services.map { service ->
                ServiceItem(
                    amount = service.amount,
                    code = service.code,
                    durationCode = service.durationCode,
                    durationId = service.durationId,
                    durationTitle = service.durationTitle,
                    enabled = service.enabled,
                    isFixedAmount = service.isFixedAmount,
                    isWonderful = service.isWonderful,
                    maxAmount = service.maxAmount,
                    minAmount = service.minAmount,
                    name = service.name,
                    vat = service.vat,
                )
            }
        )

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
}