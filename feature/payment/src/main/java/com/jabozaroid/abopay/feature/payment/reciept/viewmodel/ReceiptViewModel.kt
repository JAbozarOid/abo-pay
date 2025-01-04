package com.jabozaroid.abopay.feature.payment.reciept.viewmodel

import com.jabozaroid.abopay.core.common.util.share.shareReceipt
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptAction
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptEvent
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  Created on 8/28/2024 
 **/
@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val sharedPrefStorage: SharedPrefStorage,

    ) : BaseViewModel<ReceiptUiModel, ReceiptAction, ReceiptEvent>(
    initialState = ReceiptUiModel()
) {

    override val onRefresh: () -> Unit
        get() = {

        }

    override fun handleAction(action: ReceiptAction) {
        action.let {
            when (action) {
                is ReceiptAction.ReceiptButtonDoneClicked -> navigateToMainMenu()

                is ReceiptAction.ShareReceipt -> shareReceipt(
                    action.context,
                    action.picture,
                    action.pageWidth,
                    action.pageHeight,
                    action.sharingFileType
                )

                is ReceiptAction.SharePlainReceipt -> shareReceipt(action.context, action.text)
                is ReceiptAction.OnUpdateReceiptModel -> updateState {

                    it.copy(
                        receiptModel = getReceiptDataModel() ?: com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel(
                            visualItems = mutableListOf(),
                            paymentItems = mutableListOf(),
                            commonItems = com.jabozaroid.abopay.core.common.model.CommonItems()
                        )
                    )
                }
            }
        }
    }

    private fun getReceiptDataModel(): com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel? {
        return sharedPrefStorage.get(StorageKey.RECEIPT_MODEL, com.jabozaroid.abopay.core.common.model.PaymentConfirmationModel::class.java)
    }

    private fun navigateToMainMenu() {
        navigateTo(
            command = NavigationCommand.ToScreen(
                ApplicationRoutes.homeScreenRoute, clearBackStack = true
            )
        )
    }


}


