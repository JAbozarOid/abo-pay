package com.jabozaroid.abopay.feature.home.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import com.jabozaroid.abopay.core.ui.model.IViewState


data class HomeUiModel(
    override val loading: Boolean = true,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val phoneNumber: String = "",
    val homeServices: HomeService? = null,
) : IViewState
