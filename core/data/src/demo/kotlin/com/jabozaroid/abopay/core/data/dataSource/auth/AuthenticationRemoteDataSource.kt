package com.jabozaroid.abopay.core.data.dataSource.auth

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult

interface AuthenticationRemoteDataSource {

    suspend fun getLoginOtp(
        params: LoginOtpParam,
    ): AboPayResult<LoginOTPResult?>

    suspend fun verifyOtp(
        params: VerifyLoginOtpParam,
    ): AboPayResult<VerifyLoginOtpResult?>
}