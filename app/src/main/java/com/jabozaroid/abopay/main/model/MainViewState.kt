package com.jabozaroid.abopay.main.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.user.User
import com.jabozaroid.abopay.core.model.iva.DarkThemeConfig
import com.jabozaroid.abopay.core.model.iva.ThemeBrand
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes

internal data class
MainViewState(
    override val loading: Boolean = true,
    override val hasError: Boolean = false,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    val themeBrand: ThemeBrand = ThemeBrand.ANDROID,
    val useDynamicColor: Boolean = false,
    var startDestination: String = ApplicationRoutes.homeGraphRoute,
    val user: User? = null,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
) : IViewState
