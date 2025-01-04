package com.jabozaroid.abopay.feature.payment.paymentConfirmation.viewmodel

import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.model.PaymentConfirmationAction
import com.jabozaroid.abopay.feature.payment.paymentConfirmation.model.PaymentConfirmationUiModel
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 *  Created on 8/28/2024 
 **/
@HiltViewModel
class PaymentConfirmationViewModel @Inject constructor(
    private val sharedPrefStorage: SharedPrefStorage
) : BaseViewModel<PaymentConfirmationUiModel, PaymentConfirmationAction, ReceiptEvent>(
    initialState = PaymentConfirmationUiModel()
) {

    override val onRefresh: () -> Unit
        get() = { }

    private val _cardItem = MutableStateFlow("")
    val cardItem: StateFlow<String>
        get() = _cardItem

    fun setName(cardNumber: String) {
        _cardItem.value = cardNumber
    }


    override fun handleAction(action: PaymentConfirmationAction) {
        action.let {
            when (it) {
                PaymentConfirmationAction.OnCancelButtonClicked -> navigateBack()
                is PaymentConfirmationAction.ConfirmButtonClicked -> savePaymentModel(it.model)
                is PaymentConfirmationAction.OnUpdateConfirmationModel -> updateModel(it.dataModel)
            }
        }
    }


    private fun updateModel(data: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel) {
        updateState {
            it.copy(
                amountWithVat = getAmount(data.commonItems.amount, data.commonItems.vat)
            )
        }
        updateState {
            it.copy(
                paymentConfirmationModel = data
            )
        }
    }

    private fun getAmount(amount: String, vat: String): String {
        var amountWithVat = amount
        if (vat.isNotBlank()) {
            val price = FormatUtil.getAmountWithVat(
                amount,
                vat
            )
            if (price != 0L)
                amountWithVat = price.toString()
        }
        return amountWithVat
    }

     private fun savePaymentModel(model: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel){
         model.commonItems.amount=currentState.amountWithVat
         sharedPrefStorage.save(StorageKey.PAYMENT_MODEL , model)
         navigateToPaymentScreen()
     }
    private fun navigateToPaymentScreen() {
        navigateTo(
            command = NavigationCommand.ToScreen(ApplicationRoutes.paymentScreenRoute))
    }


}


