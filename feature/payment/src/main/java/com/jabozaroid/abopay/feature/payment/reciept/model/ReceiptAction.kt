package com.jabozaroid.abopay.feature.payment.reciept.model

import android.app.Activity
import android.graphics.Picture
import com.jabozaroid.abopay.core.common.util.share.SharingFileType
import com.jabozaroid.abopay.core.ui.model.IAction

/**
 *  Created on 8/28/2024 
 **/
sealed interface ReceiptAction : IAction {

    data object ReceiptButtonDoneClicked : ReceiptAction
    data object OnUpdateReceiptModel : ReceiptAction

    data class ShareReceipt(
        val context: Activity,
        val picture: Picture,
        val pageWidth: Float,
        val pageHeight: Float,
        val sharingFileType: SharingFileType
    ) : ReceiptAction

    data class SharePlainReceipt(
        val context: Activity,
        val text: String
    ) : ReceiptAction
}

