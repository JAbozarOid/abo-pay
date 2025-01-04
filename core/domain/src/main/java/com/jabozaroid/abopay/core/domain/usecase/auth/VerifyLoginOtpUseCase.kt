package com.jabozaroid.abopay.core.domain.usecase.auth

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult
import com.jabozaroid.abopay.core.domain.repository.auth.AuthenticationRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 *  Created on 8/21/2024 
 **/
class VerifyLoginOtpUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    BaseUseCase<VerifyLoginOtpParam, AboPayResult<VerifyLoginOtpResult?>>() {
    override suspend fun onExecute(param: VerifyLoginOtpParam): AboPayResult<VerifyLoginOtpResult?> {
        return authenticationRepository.verifyOtp(param)
    }
}