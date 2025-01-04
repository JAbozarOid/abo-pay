package com.jabozaroid.abopay.core.network.dataSource.login

import com.jabozaroid.abopay.core.data.dataSource.auth.AuthenticationRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult
import com.jabozaroid.abopay.core.network.api.auth.AuthenticationApi
import com.jabozaroid.abopay.core.network.dataSource.login.mapper.mapToDomainModel
import com.jabozaroid.abopay.core.network.dataSource.login.mapper.mapToNetworkModel
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi,
) : AuthenticationRemoteDataSource {

    override suspend fun getLoginOtp(params: LoginOtpParam): AboPayResult<LoginOTPResult?> = execute {
        withContext(Dispatchers.IO) {
            authenticationApi.getOtp(params.mapToNetworkModel())
        }
    }.map {
        it.data?.mapToDomainModel()
    }

    override suspend fun verifyOtp(params: VerifyLoginOtpParam): AboPayResult<VerifyLoginOtpResult?> =
        execute {
            withContext(Dispatchers.IO) {
                authenticationApi.verifyOtp(params.mapToNetworkModel())
            }
        }.map {
            it.data?.mapToDomainModel()
       }


}


