package com.jabozaroid.abopay.feature.kahroba.auth.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

data class KahrobaAuthUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val nationalCode: NationalCodeModel = NationalCodeModel(),
    val password : Password = Password(),
    val confirmPassword : ConfirmPassword = ConfirmPassword()
) : IViewState {

    fun isContinueBtnEnable(): Boolean {
        return nationalCode.errorMessage == null && nationalCode.value.isNotBlank() &&
                password.errorMessage == null && password.value.isNotBlank() &&
                confirmPassword.errorMessage == null && confirmPassword.value.isNotBlank()
    }

}

data class NationalCodeModel(
    var value: String = "",
    @StringRes val errorMessage: Int? = null,
)
data class Password(
    var value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class ConfirmPassword(
    var value: String = "",
    @StringRes val errorMessage: Int? = null,
)