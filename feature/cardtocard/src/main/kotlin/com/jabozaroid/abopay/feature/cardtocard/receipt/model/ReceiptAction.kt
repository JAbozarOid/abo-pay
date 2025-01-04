package com.jabozaroid.abopay.feature.cardtocard.receipt.model

import android.app.Activity
import android.graphics.Picture
import com.jabozaroid.abopay.core.common.util.share.SharingFileType
import com.jabozaroid.abopay.core.ui.model.IAction

/**
 *  Created on 10/21/2024
 **/
sealed interface ReceiptAction  : IAction {

    data class ShareReceipt(
        val context: Activity,
        val picture: Picture,
        val pageWidth: Float,
        val pageHeight: Float,
        val sharingFileType: SharingFileType
    ) : ReceiptAction

    data object  OnBackButtonClicked : ReceiptAction
    data class SharePlainReceipt(
        val context: Activity,
        val text: String
    ) : ReceiptAction

    data object OnUpdateCardInfo: ReceiptAction

}