package com.jabozaroid.abopay.core.domain.repository.auth

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult


interface AuthenticationRepository {
    suspend fun getLoginOtp(param: LoginOtpParam): AboPayResult<LoginOTPResult?>
    suspend fun verifyOtp(param: VerifyLoginOtpParam): AboPayResult<VerifyLoginOtpResult?>
}