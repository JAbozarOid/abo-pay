package com.jabozaroid.abopay.core.domain.usecase.auth

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.repository.auth.AuthenticationRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject


class GetLoginOtpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) :
    BaseUseCase<LoginOtpParam, AboPayResult<LoginOTPResult?>>() {

    override suspend fun onExecute(param: LoginOtpParam): AboPayResult<LoginOTPResult?> {
        return authenticationRepository.getLoginOtp(param)
    }

}