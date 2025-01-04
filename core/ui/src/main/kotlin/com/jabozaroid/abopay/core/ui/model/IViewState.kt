package com.jabozaroid.abopay.core.ui.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError

interface IViewState {
    val loading: Boolean
    val hasError: Boolean
    val aboPayException: AboPayExceptionMessage
    val aboPayApiError: AboPayServerError
}