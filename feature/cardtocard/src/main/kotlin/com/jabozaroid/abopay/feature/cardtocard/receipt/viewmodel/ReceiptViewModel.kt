package com.jabozaroid.abopay.feature.cardtocard.receipt.viewmodel

import com.jabozaroid.abopay.core.common.model.MetaDataModel
import com.jabozaroid.abopay.core.common.util.DateUtil
import com.jabozaroid.abopay.core.common.util.share.shareReceipt
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.C2CTransfer
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptAction
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptEvent
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptItemModel
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  Created on 10/21/2024
 **/
@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val sharedPrefStorage: SharedPrefStorage
) : BaseViewModel<ReceiptUiModel, ReceiptAction, ReceiptEvent>(
    initialState = ReceiptUiModel()
) {

    override val onRefresh: () -> Unit
        get() = {

        }

    override fun handleAction(action: ReceiptAction) {
        when (action) {
            is ReceiptAction.ShareReceipt -> shareReceipt(
                action.context,
                action.picture,
                action.pageWidth,
                action.pageHeight,
                action.sharingFileType
            )

            is ReceiptAction.SharePlainReceipt -> shareReceipt(action.context, action.text)
            ReceiptAction.OnBackButtonClicked -> navigateToMainMenu()
            is ReceiptAction.OnUpdateCardInfo -> updateReceiptItemModel()
        }
    }

    private fun navigateToMainMenu() {
        navigateTo(
            command = NavigationCommand.ToScreen(
                ApplicationRoutes.homeScreenRoute, clearBackStack = true
            )
        )
    }

    private fun getReceiptDataModel(): C2CTransfer? {
        return sharedPrefStorage.get(StorageKey.C2C_TRANSFER_RESULT, C2CTransfer::class.java)
    }

    private fun updateReceiptItemModel() {
        val transferResult = getReceiptDataModel() ?: return
        updateState {
            it.copy(
                transferResultModel = transferResult
            )
        }

        updateState {
            it.copy(
                receiptItemModel = createItemModel(transferResult)
            )
        }

    }

    private fun createItemModel(data: C2CTransfer): ReceiptItemModel {
        return ReceiptItemModel(
            amount = data.amount,
            dateTime = DateUtil.convertPersianDate(data.transactionDate),
            metadataItems = listOf(

                MetaDataModel(
                    "صاحب کارت مقصد",
                    data.destinationPanOwner
                ),
                MetaDataModel(
                    "شماره کارت مقصد",
                    data.destinationPan
                ),
                MetaDataModel(
                    " شماره کارت مبدا",
                    data.sourcePan
                ),
                MetaDataModel(
                    "شَماره پیگیری",
                    data.transactionId
                ),
                MetaDataModel(
                    "شماره ارجاع ",
                    data.rrn
                ),
                MetaDataModel(
                    "شماره ترمینال",
                    data.stan
                )
            ),
        )
    }
}