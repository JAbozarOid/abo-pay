package com.jabozaroid.abopay.core.common.util

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object BrowserUtil {

    @Composable
    fun openBrowser(url : String) {
        LocalContext.current.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(url)))
    }
}