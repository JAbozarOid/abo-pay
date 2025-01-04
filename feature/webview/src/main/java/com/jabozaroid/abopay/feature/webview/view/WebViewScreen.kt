package com.jabozaroid.abopay.feature.webview.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.webview.model.HttpMethodType
import com.jabozaroid.abopay.feature.webview.model.WebViewAction
import com.jabozaroid.abopay.feature.webview.model.WebViewEvent
import com.jabozaroid.abopay.feature.webview.model.WebViewUiModel
import com.jabozaroid.abopay.feature.webview.model.WebViewUrlItem
import com.jabozaroid.abopay.feature.webview.viewmodel.WebViewModel
import java.nio.charset.Charset

/**
 *  Created on 8/5/2024 
 **/
class WebViewScreen :
    BaseScreen<WebViewUiModel, WebViewAction, WebViewEvent>(
        name = "BaseWebView",
        route = ApplicationRoutes.webViewScreenRoute + ApplicationRoutes.webViewParam
    ) {

    private lateinit var webView: WebView

    @Composable
    override fun ViewModel(): WebViewModel = hiltViewModel()

    @Composable
    override fun Content(state: WebViewUiModel) {

        val viewModel = ViewModel()

        val context = LocalContext.current
        webView = WebView(context)

        handleBackPress()

        setDefaultClient {
            viewModel.process(
                WebViewAction.OnLoadFinished(it)
            )
        }
        SetBasicSettings()
        viewModel.process(action = WebViewAction.OnLoadData)
        loadUrl(webViewUrlItem = state.urlItem)
    }

    private fun handleBackPress() {
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
    private fun SetBasicSettings() {

        AndroidView(factory = {
            webView.apply {
                val settings = webView.settings
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.allowFileAccess = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                webView.webViewClient = WebViewClient()
                WebView.setWebContentsDebuggingEnabled(true)
                settings.mediaPlaybackRequiresUserGesture = false
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.builtInZoomControls = false
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.userAgentString =
                    "Mozilla/5.0 (Linux; Android 7.1; vivo 1716 Build/N2G47H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36"
            }
        }
        )

    }

    private fun setDefaultClient(onPageLoaded: (Boolean) -> Unit) {
        webView.webViewClient = object : WebViewClient() {
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
                onPageLoaded(true)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                onPageLoaded(true)
            }

        }
        webView.clearCache(true)
    }

    private fun loadUrl(webViewUrlItem: WebViewUrlItem) {
        when (webViewUrlItem.getHttpMethodType) {
            HttpMethodType.None -> {
                webView.loadUrl(webViewUrlItem.getUrl)
            }

            HttpMethodType.Get -> {
                webView.loadUrl(webViewUrlItem.getUrl)
            }

            HttpMethodType.Post -> {
                val test = webViewUrlItem.getBody?.toString(Charset.defaultCharset())
                Log.d("MAMAD", "BODY :::::::::    ${test}")

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

}
