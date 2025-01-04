package com.jabozaroid.abopay.feature.webview.viewmodel

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.webview.model.WebViewAction
import com.jabozaroid.abopay.feature.webview.model.WebViewEvent
import com.jabozaroid.abopay.feature.webview.model.WebViewUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import javax.inject.Inject


@HiltViewModel
class WebViewModel @Inject constructor(
) : BaseViewModel<WebViewUiModel, WebViewAction, WebViewEvent>(
    initialState = WebViewUiModel()
) {
    @Inject
    lateinit var sharedPrefStorage: SharedPrefStorage

    override val onRefresh: () -> Unit
        get() = { }

    override fun handleAction(action: WebViewAction) {
        when (action) {

            is WebViewAction.OnLoadData -> {
                getPostBodyData()

            }

            is WebViewAction.OnLoadFinished ->
                onPageLoaded(action.isPageLoaded)
        }
    }

    private fun onPageLoaded(pageLoaded: Boolean) {
        updateState {
            it.copy(
                loading = !pageLoaded
            )
        }

    }


    private fun getPostBodyData() {
        val postData = "token=${URLEncoder.encode(getToken(), "UTF-8")}"

        updateState {
            it.copy(
                urlItem = it.urlItem.copy(
                    getBody = postData.toByteArray()
                )
            )
        }
    }

    private fun getToken() : String{
        var token : String?
        runBlocking {
            token = sharedPrefStorage.getString(StorageKey.TOKEN)
        }
        return token ?: ""
    }
}


