package com.jabozaroid.abopay.feature.webview.model

import com.jabozaroid.abopay.core.ui.model.IAction

/**
 *  Created on 8/5/2024
 **/
sealed interface WebViewAction : IAction {
    data object OnLoadData : WebViewAction
    data class OnLoadFinished(val isPageLoaded : Boolean) : WebViewAction
}