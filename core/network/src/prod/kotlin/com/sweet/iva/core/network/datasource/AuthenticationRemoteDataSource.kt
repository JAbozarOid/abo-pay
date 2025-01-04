package com.sweet.iva.core.network.datasource

import com.sweet.iva.core.network.helper.parseResponse
import com.sweet.iva.core.network.service.auth.AuthenticationApi
import com.sweet.iva.core.network.model.LoginOtpNetworkParam
import com.sweet.iva.core.network.model.LoginOtpNetworkResult
import javax.inject.Inject


class AuthenticationRemoteDataSource @Inject constructor(
    private val authApi: AuthenticationApi
) {

    suspend fun sendLoginOtp(
        params: LoginOtpNetworkParam
    ): LoginOtpNetworkResult {
        return authApi.sendLoginOtp(params).parseResponse()
    }

}