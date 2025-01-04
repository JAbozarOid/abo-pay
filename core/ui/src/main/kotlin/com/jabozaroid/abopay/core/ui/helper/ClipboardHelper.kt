package com.jabozaroid.abopay.core.ui.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipboardHelper {

    fun copyToClipBoard(context: Context, data: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText("aboPay", data)
        )

    }

}