package com.jabozaroid.abopay.feature.kahroba.auth.viewmodel

import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.navigation.NavigationCommand
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthAction
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthEvent
import com.jabozaroid.abopay.feature.kahroba.auth.model.KahrobaAuthUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KahrobaAuthViewModel @Inject constructor() :
    BaseViewModel<KahrobaAuthUiModel, KahrobaAuthAction, KahrobaAuthEvent>(
        initialState = KahrobaAuthUiModel()
    ) {

    override val onRefresh: () -> Unit
        get() = {}

    override fun handleAction(action: KahrobaAuthAction) {
        when (action) {
            is KahrobaAuthAction.NavigateUp -> {
                navigateBack()
            }

            is KahrobaAuthAction.OnNationalCodeChanged -> {
                validateNationalCode(action.nationalCode)
            }

            is KahrobaAuthAction.OnPasswordChanged -> {
                validationPassword(action.password)
            }

            is KahrobaAuthAction.OnConfirmPasswordChanged -> {
                validationConfirmPassword(action.confirmPassword, action.matchPassword)
            }

            is KahrobaAuthAction.OnContinueBtnClicked -> {
                navigateTo(
                    NavigationCommand.ToScreen(
                        ApplicationRoutes.kahrobaScreenRoute
                    )
                )
            }
        }
    }


    private fun validateNationalCode(nationalCode: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (nationalCode.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_national_id
        } else if (ValidationUtil.nationalCode(nationalCode) != ValidationState.VALID) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_national_id
        }

        updateState {
            it.copy(
                nationalCode = it.nationalCode.copy(
                    errorMessage = errorMessage,
                    value = nationalCode
                )
            )
        }

        return isValid
    }

    private fun validationPassword(password: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (password.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_password
        } else if (ValidationUtil.password(password) != ValidationState.VALID) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_password
        }

        updateState {
            it.copy(
                password = it.password.copy(
                    errorMessage = errorMessage,
                    value = password
                )
            )
        }

        return isValid
    }

    private fun validationConfirmPassword(confirmPassword: String, matchPassword: String): Boolean {
        var isValid = true
        var errorMessage: Int? = null

        if (confirmPassword.isBlank()) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.enter_confirm_password
        } else if (ValidationUtil.confimPassword(
                confirmPassword,
                matchPassword
            ) != ValidationState.VALID
        ) {
            isValid = false
            errorMessage = com.jabozaroid.abopay.core.common.R.string.invalid_confirm_password
        }

        updateState {
            it.copy(
                confirmPassword = it.confirmPassword.copy(
                    errorMessage = errorMessage,
                    value = confirmPassword
                )
            )
        }

        return isValid
    }

}