package com.jabozaroid.abopay.feature.webview.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.jabozaroid.abopay.core.designsystem.component.AppLoadingWheel
import com.jabozaroid.abopay.feature.webview.model.HttpMethodType
import com.jabozaroid.abopay.feature.webview.model.WebViewUrlItem

/**
 *  Created on 8/5/2024 
 **/


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoadWebView(
    webViewUrlItem: WebViewUrlItem
) {
    val showLoading = remember {
        mutableStateOf(true)
    }

    if (showLoading.value) AppLoadingWheel(contentDesc = "loading")

    BuildWebView(webViewUrlItem, showLoading)
}

private fun handleBackPress(webView: WebView) {
    webView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webView.canGoBack()) {
            webView.goBack()
            return@OnKeyListener true
        }
        false
    })
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun BuildWebView(webViewUrlItem: WebViewUrlItem, showLoading: MutableState<Boolean>) {
    AndroidView(factory = { context ->
        val webView = WebView(context)
        handleBackPress(webView)


        webView.apply {
            webViewClient = setDefaultClient(this, showLoading)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowFileAccess = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            setWebContentsDebuggingEnabled(true)
            settings.mediaPlaybackRequiresUserGesture = false
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.builtInZoomControls = false
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.userAgentString =
                "Mozilla/5.0 (Linux; Android 7.1; vivo 1716 Build/N2G47H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36"
            settings.setSupportZoom(false)
        }

    },
        update = {
            loadUrl(it, webViewUrlItem)
        }
    )
}

private fun setDefaultClient(webView: WebView, showLoading: MutableState<Boolean>): WebViewClient {
    val webViewClient = object : WebViewClient() {

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            //we don't need override this method because our minSdk is above LOLLIPOP
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (view?.progress == 100) {
                showLoading.value = false
            }
        }

    }
    webView.clearCache(true)
    return webViewClient
}

private fun loadUrl(webView: WebView, webViewUrlItem: WebViewUrlItem) {
    when (webViewUrlItem.getHttpMethodType) {
        HttpMethodType.None -> {
            webView.loadUrl(webViewUrlItem.getUrl)
        }

        HttpMethodType.Get -> {
            webView.loadUrl(webViewUrlItem.getUrl)
        }

        HttpMethodType.Post -> {
            webView.postUrl(webViewUrlItem.getUrl, webViewUrlItem.getBody ?: "".toByteArray())
        }

        HttpMethodType.AuthorizedGet -> {
            webView.postUrl(webViewUrlItem.getUrl, webViewUrlItem.getBody ?: "".toByteArray())
        }

        HttpMethodType.AuthorizedPost -> {
            webView.postUrl(webViewUrlItem.getUrl, webViewUrlItem.getBody ?: "".toByteArray())
        }

    }
}