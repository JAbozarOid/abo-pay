package com.jabozaroid.abopay.feature.webview.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState


data class WebViewUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    val urlItem: WebViewUrlItem =
        WebViewUrlItem(
            HttpMethodType.Post,
            "http://172.24.204.17:5002/",
            null
        ),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
): IViewState


data class WebViewUrlItem(
    val getHttpMethodType: HttpMethodType = HttpMethodType.None,
    val getUrl: String = "",
    val getBody: ByteArray? = null,
)  {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebViewUrlItem

        if (getHttpMethodType != other.getHttpMethodType) return false
        if (getUrl != other.getUrl) return false
        if (getBody != null) {
            if (other.getBody == null) return false
            if (!getBody.contentEquals(other.getBody)) return false
        } else if (other.getBody != null) return false

        return true
    }

    override fun hashCode(): Int {
        return getBody?.contentHashCode() ?: 0
    }
}

