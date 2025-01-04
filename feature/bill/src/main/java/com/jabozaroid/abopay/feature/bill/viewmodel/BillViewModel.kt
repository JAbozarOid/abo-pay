package com.jabozaroid.abopay.feature.bill.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentParam
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.DeleteBillParam
import com.jabozaroid.abopay.core.domain.onAboPayApiError
import com.jabozaroid.abopay.core.domain.onAboPayException
import com.jabozaroid.abopay.core.domain.onAboPaySuccess
import com.jabozaroid.abopay.core.domain.usecase.bill.AddBillUsecase
import com.jabozaroid.abopay.core.domain.usecase.bill.DeleteBillUsecase
import com.jabozaroid.abopay.core.domain.usecase.bill.EditeBillUsecase
import com.jabozaroid.abopay.core.domain.usecase.bill.GetBillInquiryUsecase
import com.jabozaroid.abopay.core.domain.usecase.bill.GetBillTypesUsecase
import com.jabozaroid.abopay.core.domain.usecase.bill.GetFrequentBillsUsecase
import com.jabozaroid.abopay.core.ui.model.IEvent
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.navigation.NavigationParam
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.bill.model.BillAction
import com.jabozaroid.abopay.feature.bill.model.BillEvent
import com.jabozaroid.abopay.feature.bill.model.BillUiModel
import com.jabozaroid.abopay.feature.bill.preview.dataModel
import com.jabozaroid.abopay.feature.bill.preview.frequentList
import com.jabozaroid.abopay.feature.bill.util.billId
import com.jabozaroid.abopay.feature.bill.util.billIdAndPayId
import com.jabozaroid.abopay.feature.bill.util.getAmount
import com.jabozaroid.abopay.feature.bill.util.getBillIconThemeByTypeId
import com.jabozaroid.abopay.feature.bill.util.getBillType
import com.jabozaroid.abopay.feature.bill.util.payId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BillViewModel @Inject constructor(
    private val getBillTypesUsecase: GetBillTypesUsecase,
    private val addBillUsecase: AddBillUsecase,
    private val editeBillUsecase: EditeBillUsecase,
    private val deleteBillUsecase: DeleteBillUsecase,
    private val getBillInquiryUsecase: GetBillInquiryUsecase,
    private val getFrequentBillsUsecase: GetFrequentBillsUsecase,
) :
    BaseViewModel<BillUiModel, BillAction, BillEvent>(
        initialState = BillUiModel()
    ) {

    private lateinit var billTypes: BillsTypeResult

    companion object {
        const val TAG = "BillViewModel"
    }

    override val onRefresh: () -> Unit
        get() = {
            updateState {
                it.copy(hasError = false)
            }
            getBillTypes()
        }

    init {
        getBillTypes()
        getFrequentBills()
    }

    private fun getBillTypes() {

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
            }) {
            updateState {
                it.copy(loading = true)
            }

            getBillTypesUsecase.execute(Unit)
                .onAboPayException { throwable ->
                    Log.d(TAG, "GetBillTypes onError: ${throwable.message}")
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                    updateState {
                        it.copy(loading = false, aboPayException = throwable)
                    }
                }
                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "GetBillTypes onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                    )
                    updateState {
                        it.copy(loading = false, hasError = true, aboPayApiError = apiError)
                    }
                }
                .onAboPaySuccess { billsTypeResult ->
                    billsTypeResult?.let { billTypeListResult ->
                        billTypes = billTypeListResult
                        val billTypesList = billTypeListResult.billTypes.toMutableList()
                        val indexedBillTypeList: List<BillTypeResult> =
                            billTypesList.mapIndexed { index, operatorItem ->
                                operatorItem.copy(index = index)
                            }.toList()
                        updateState {
                            it.copy(
                                billTypeItems = indexedBillTypeList.toIconItemModel(),
                                loading = false
                            )
                        }
                    }
                }

        }
    }

    private fun List<BillTypeResult>.toIconItemModel(): List<IconItemUiModel> {
        val mapList: MutableList<IconItemUiModel> = mutableListOf()
        for ((index, item) in this.withIndex()) {
            mapList.add(
                index,
                IconItemUiModel(
                    lightLogo = item.lightLogo,
                    darkLogo = item.darkLogo,
                    title = item.billTypeName,
                    index = item.index,
                    model = item,
                )
            )
        }
        return mapList
    }

    private fun addBill(param: AddBillParam) {
        viewModelScope.launch {
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

            addBillUsecase.execute(param)
                .onAboPayException { throwable ->
                    Log.d(TAG, "AddBill onError: ${throwable.message}")
                    updateState {
                        it.copy(
                            loading = false,
                            aboPayException = throwable
                        )
                    }
                    sendEvent(IEvent.ShowSnackMessage(throwable.text))
                }
                .onAboPaySuccess {
                    sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.success_add_bill))
                }

                .onAboPayApiError { apiError ->
                    Log.d(
                        TAG,
                        "AddBill onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                    )
                    updateState {
                        it.copy(
                            loading = false,
                        )
                    }
                    sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))

                }

        }
    }

    private fun editeBill(editedBill: AddBillParam) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
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
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_edit_data))
        }) {


            val result = editeBillUsecase.execute(editedBill)

            result.onAboPayException { throwable ->
                Log.d(TAG, "EditeBill onError: ${throwable.message}")
                updateState {
                    it.copy(loading = false)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))

            }
            result.onAboPaySuccess {
                sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.success_edit_bill))
                getFrequentBills()
                updateState {
                    it.copy(loading = false)
                }
            }

            result.onAboPayApiError { apiError ->
                Log.d(
                    TAG,
                    "EditeBill onFailure: code: ${apiError.error.code} message : ${apiError.error.message}"
                )
                updateState {
                    it.copy(
                        loading = false,
                    )
                }
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))

            }
        }

        var bills: List<FrequentUiModel>
        updateState {
            bills = it.frequentBills
            val result = bills.filter {
                it.id.toString() != editedBill.id
            }.toList()

            it.copy(
                frequentBills = result.toMutableList(),
                editeBottomSheet = it.editeBottomSheet.copy(
                    isVisible = false,
                ),
            )
        }
    }

    private fun deleteBill(item: FrequentUiModel) {

        val deleteParam = DeleteBillParam(
            billId = item.secondText ?: "",
            billType = item.id ?: -1
        )
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            updateState {
                it.copy(loading = true)
            }

            if (throwable is com.jabozaroid.abopay.core.common.model.DisplayException) {
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                return@CoroutineExceptionHandler
            }
            throwable.message?.let {
                sendEvent(IEvent.ShowSnackMessage(it))
            }
                ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
        }) {

//            val result = deleteBillUsecase.execute(deleteParam)

//            result.onError { throwable ->
//                Log.d(TAG, "DeleteBill onError: ${throwable.message}")
//                updateState {
//                    it.copy(loading = false)
//                }
//                sendEvent(IEvent.ShowSnack("${throwable.message}"))
//
//            }
//            result.onSuccess {
            sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.success_delete_bill))
            val newBillList = currentState.frequentBills
            newBillList.remove(item)
            updateState {
                it.copy(
                    loading = false,
                    removeBottomSheet = it.removeBottomSheet.copy(
                        visible = false
                    ),
                    frequentBills = newBillList.toMutableList()
                )
            }
//            }
//            result.onFailure { code, message ->
//                Log.d(TAG, "DeleteBill onFailure: code: $code message : $message")
//                updateState {
//                    it.copy(
//                        loading = false,
//                    )
//                }
//                sendEvent(IEvent.ShowSnack("code:$code --> message:$message"))
//
//            }
        }
    }

    private fun validateBillInput(type: Int, billId: String): Boolean {
//        if (type == BillType.MOBILE_BILL_TYPE.ordinal) {
        if (ValidationUtil.Validate.mobile(billId) == ValidationState.EMPTY) {
            updateState {
                it.copy(
                    addBillBottomSheet = it.addBillBottomSheet.copy(
                        error = com.jabozaroid.abopay.core.common.R.string.empty_phone_number
                    )
                )
            }
            return false
        }
//        }
        return true
    }

    private fun validateBillIdInput(billId: String): Boolean {
        if (ValidationUtil.Validate.mobile(billId) == ValidationState.EMPTY) {
            updateState {
                it.copy(
                    addBillBottomSheet = it.addBillBottomSheet.copy(
                        error = com.jabozaroid.abopay.core.common.R.string.empty_phone_number
                    )
                )
            }
            return false
        } else {
            updateState {
                it.copy(
                    addBillBottomSheet = it.addBillBottomSheet.copy(
                        error = null
                    )
                )
            }
            return true
        }
    }

    private fun getBillInquiry(param: BillInquiryParam) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
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
        }) {
            updateState {
                it.copy(loading = true)
            }
            param.id?.let {
                if (!validateBillInput(param.type, it)) {
                    updateState {
                        it.copy(loading = true)
                    }
                    return@launch
                }
            }

            val result = getBillInquiryUsecase.execute(param)

            result.onAboPayException { throwable ->
                Log.d(TAG, "GetBillInquiry onError: ${throwable.message}")
                updateState {
                    it.copy(loading = false)
                }
                throwable.message?.let {
                    sendEvent(IEvent.ShowSnackMessage(it))
                }
                    ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
            }

            result.onAboPaySuccess { billInquiryResult ->

                billInquiryResult?.let { billInquiry ->
//                    billInquiryResult.billInquiryList?.let { billInquiryItemResult ->
                    updateState {
                        it.copy(
                            loading = false,
                            addBillBottomSheet = it.addBillBottomSheet.copy(
                                isVisible = false,
                                billType = null
                            ),
                            inquiryBottomSheet = it.inquiryBottomSheet.copy(
                                uiBillInquiry = billInquiry,
                                isVisible = true
                            ),
                        )
                    }

//                    }
                }

            }
            result.onAboPayApiError { apiError ->
                Log.d(
                    TAG,
                    "GetBillInquiry onApiErrorHandler: code: ${apiError.error.code} message : ${apiError.error.message}"
                )
                updateState {
                    it.copy(
                        loading = false,
                        billInquiry = listOf(),
                        addBillBottomSheet = it.addBillBottomSheet.copy(
                            isVisible = false,
                            billType = null,
                            error = null
                        )

                    )
                }
                sendEvent(IEvent.ShowSnackMessage("code:${apiError.error.code} --> message:${apiError.error.message}"))
            }
        }
    }

    private fun getFrequentBills() {
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
            getFrequentMockList()

//            val result = getFrequentBillsUsecase.execute(Unit)
//            result.onError { throwable ->
//                Log.d(TAG, "GetFrequentBills onError: ${throwable.message}")
//                updateState {
//                    it.copy(loading = false)
//                }
//                sendEvent(IEvent.ShowSnack("${throwable.message}"))
//            }
//            result.onSuccess { billsResult ->
            val frequents: MutableList<FrequentUiModel> = mutableListOf()
//                val billTypes: MutableList<BillTypeResult> = mutableListOf()
//
//                updateState {
//                    it.billTypeItems.forEach {
//                        billTypes.add(it.model as BillTypeResult)
//                    }
//                    it.copy(
//                        loading = false,
//                    )
//                }
//                billsResult?.let {
//                    it.billNetworkResults.let {
//                        it.forEach {
//                            var billTypeName = ""
//                            var billLogo = ""
//                            it.billType?.let {
//                                billTypeName = getBillName(billTypes, it) ?: ""
//                                billLogo = getBillIconThemeByTypeId(
//                                    billTypes, it,
//                                    true
//                                )
//                                //Todo:Change isDark mode for passing in this function
//                            }
//
//                            frequents.add(
//                                FrequentUiModel(
//                                    startIcon = AppIcons.icMoreBlack,
//                                    endIconURL = billLogo,
//                                    overlayIcon1 = AppIcons.icRemove,
//                                    overlayIcon2 = AppIcons.icEdit,
//                                    firstText = it.billName ?: "",
//                                    secondText = it.billParameter ?: "",
//                                    overlayText1 = "حذف",
//                                    overlayText2 = "ویرایش",
//                                    endIconTint = null,
//                                    id = it.billType.toString(),
//                                    billTypeName = billTypeName
//                                )
//                            )
////                        }
////                    }
//                    updateState {
//                        it.copy(
//                            frequentBills = frequents
//                        )
//                    }
//                }
//
//            }
        }
    }

    private fun getFrequentMockList() {
        viewModelScope.launch {

            delay(2000)

            updateState {
                it.copy(
                    frequentBills = frequentList
                )
            }
        }

    }

    private fun showAddBillBottomSheet(billType: BillTypeResult) {
        updateState {
            it.copy(
                addBillBottomSheet = it.addBillBottomSheet.copy(
                    isVisible = true,
                    billType = billType
                )
            )
        }
    }

    private fun hideAddBillBottomSheet() {
        updateState {
            it.copy(
                addBillBottomSheet =
                it.addBillBottomSheet.copy(
                    billType = null,
                    isVisible = false
                )
            )
        }
    }

    //In PaymentWith BillId just find type and amount and then go to payment
    private fun confirmPayment(param: BillPaymentParam) {

        if (ValidationState.VALID == billId(param.billId)) {
            if (ValidationState.VALID == payId(param.paymentId)) {
                try {
                    if (ValidationState.VALID == billIdAndPayId(
                            billId = param.billId,
                            paymentId = param.paymentId
                        )
                    ) {
                        val amount = getAmount(param.paymentId ?: "").toString()
                        val billType = getBillType(param.billId)
                        val logo = getBillIconThemeByTypeId(billTypes.billTypes, billType, false)
                        val completeData = BillPaymentParam(
                            billId = param.billId,
                            paymentId = param.paymentId,
                            amount = amount,
                            logo = logo
                        )
                        updateState {
                            it.copy(
                                loading = false,
                                paymentBottomSheet = it.paymentBottomSheet.copy(
                                    isVisible = true,
                                    uiBillPaymentParam = completeData
                                )
                            )
                        }
                    } else {
                        sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.incompatible_bill_info))
                    }
                } catch (e: IllegalArgumentException) {
                    e.message?.let {
                        sendEvent(IEvent.ShowSnackMessage(it))
                    }
                        ?: sendEvent(IEvent.ShowSnack(com.jabozaroid.abopay.core.common.R.string.error_fetch_data))
                }

            } else {
                updateState {
                    it.copy(
                        loading = false,
                        paymentInputBottomSheet = it.paymentInputBottomSheet.copy(
                            isVisible = true,
                            billIdError = "",
                            paymentIdError = "خطا",
                            error = ""
                        )
                    )
                }
            }
        } else {
            updateState {
                it.copy(
                    loading = false,
                    paymentInputBottomSheet = it.paymentInputBottomSheet.copy(
                        isVisible = true,
                        billIdError = "خطا",
                        paymentIdError = "",
                        error = ""
                    )
                )
            }
        }
    }

    private fun showInquiryBottomSheet(billInquiryParam: BillInquiryParam) {
        getBillInquiry(billInquiryParam)
    }

    private fun hideInquiryBottomSheet() {
        updateState {
            it.copy(
                inquiryBottomSheet = it.inquiryBottomSheet.copy(
                    isVisible = false
                )
            )
        }
    }

    private fun showRemoveBottomSheet(item: FrequentUiModel) {
        updateState {
            it.copy(
                removeBottomSheet = it.removeBottomSheet.copy(
                    visible = true,
                    item = item
                )
            )
        }

    }

    private fun hideRemoveBottomSheet() {
        updateState {
            it.copy(
                removeBottomSheet = it.removeBottomSheet.copy(
                    false
                )
            )
        }
    }

    private fun showEditeBottomSheet(item: FrequentUiModel) {
        updateState {
            it.copy(
                editeBottomSheet = it.editeBottomSheet.copy(
                    isVisible = true,
                    item = item
                )
            )
        }
    }

    private fun hideEditeBottomSheet() {
        updateState {
            it.copy(
                editeBottomSheet = it.editeBottomSheet.copy(
                    false
                )
            )
        }
    }


    private fun createPaymentModel() {

        navigateToPayment(dataModel)

    }

    private fun navigateToPayment(dataModel: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel) {
        hideInquiryBottomSheet()
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

    private fun showPaymentInputBottomSheet() {
        updateState {
            it.copy(
                paymentInputBottomSheet = it.paymentInputBottomSheet.copy(
                    isVisible = true
                )
            )
        }
    }

    private fun hidePaymentInputBottomSheet() {
        updateState {
            it.copy(
                paymentInputBottomSheet = it.paymentInputBottomSheet.copy(
                    isVisible = false
                )
            )
        }
    }

    private fun hildePaymentBottomSheet() {
        updateState {
            it.copy(
                paymentBottomSheet = it.paymentBottomSheet.copy(
                    isVisible = false,
                    uiBillPaymentParam = BillPaymentParam()
                )
            )
        }
    }

    override fun handleAction(action: BillAction) {
        when (action) {
            BillAction.GetBillTypes -> getBillTypes()
            is BillAction.AddBill -> addBill(action.param)
            is BillAction.EditeBill -> editeBill(action.editedBill)
            is BillAction.GetBillInquiry -> getBillInquiry(action.param)
            is BillAction.ConfirmPaymentID -> confirmPayment(action.param)
            is BillAction.ShowAddBillBottomSheet -> showAddBillBottomSheet(action.billType)
            is BillAction.NavigateUp -> {
                hideInquiryBottomSheet()
                navigateBack()
            }

            is BillAction.ValidateBillIdData -> validateBillIdInput(action.billId)
            BillAction.GetFrequentBill -> getFrequentBills()
            BillAction.CloseAddBillBottomSheet -> hideAddBillBottomSheet()
            BillAction.HideInquiryBottomSheet -> hideInquiryBottomSheet()
            is BillAction.ShowInquiryBottomSheet -> showInquiryBottomSheet(action.param)
            BillAction.HideEditeBottomSheet -> hideEditeBottomSheet()
            BillAction.HideRemoveBottomSheet -> hideRemoveBottomSheet()
            is BillAction.ShowEditeBottomSheet -> showEditeBottomSheet(action.item)
            is BillAction.ShowRemoveBottomSheet -> showRemoveBottomSheet(action.item)
            BillAction.NavigateToPayment -> createPaymentModel()
            BillAction.HidePaymentInputBottomSheet -> hidePaymentInputBottomSheet()
            BillAction.ShowPaymentInputBottomSheet -> showPaymentInputBottomSheet()
            BillAction.HidePaymentBottomSheet -> hildePaymentBottomSheet()
            is BillAction.DeleteFrequentBill -> deleteBill(action.item)
        }
    }
}