package com.jabozaroid.abopay.feature.login.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.login.R


data class LoginUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    val mobile: MobileModel = MobileModel(),
    val nationalCode: NationalCodeModel = NationalCodeModel(),
    val otpModel: OtpModel = OtpModel(),
    val introItems: List<IntroItemModel> = listOf(
        IntroItemModel(
            "ابو پی، لذت پرداخت",
            R.drawable.ic_login_pager
        ),
        IntroItemModel(
            "خدمات جامع پرداخت",
            R.drawable.ic_login_pager
        ),
        IntroItemModel(
            "ابو پی، لذت پرداخت",
            R.drawable.ic_login_pager
        ),
    ),
) : IViewState {
    private val otpCodeLength = 6

    fun isLoginSubmitButtonEnable(): Boolean {
        return mobile.errorMessage == null && mobile.value.isNotBlank() &&
                nationalCode.errorMessage == null && nationalCode.value.isNotBlank()
    }

    fun isVerifySubmitButtonEnable(): Boolean {
        return otpModel.errorMessage == null &&
                otpModel.otpCode.isNotBlank() && otpModel.otpCode.length == otpCodeLength

    }
}

data class IntroItemModel(
    val title: String,
    val image: Int,
)

data class MobileModel(
    var value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class NationalCodeModel(
    var value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class OtpModel(
    var mobile: String = "",
    var otpCode: String = "",
    var nationalCode: String = "",
    @StringRes val errorMessage: Int? = null,
    var uniqueTraceNumber: String = "",
    var enableOtpRequest: Boolean = true,
    var timeLeft: String = "00:00",
    var timeMilliSecond: Long = -1L
)
