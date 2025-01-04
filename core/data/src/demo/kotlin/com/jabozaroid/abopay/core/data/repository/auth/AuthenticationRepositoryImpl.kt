package com.jabozaroid.abopay.core.data.repository.auth

import com.jabozaroid.abopay.core.data.dataSource.auth.AuthenticationRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult
import com.jabozaroid.abopay.core.domain.repository.auth.AuthenticationRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthenticationRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val remoteDataSource: AuthenticationRemoteDataSource,
) : AuthenticationRepository {


    override suspend fun getLoginOtp(param: LoginOtpParam): AboPayResult<LoginOTPResult?> {
        return withContext(dispatcherProvider.io) {
            return@withContext remoteDataSource.getLoginOtp(param)
        }
    }

    override suspend fun verifyOtp(param: VerifyLoginOtpParam): AboPayResult<VerifyLoginOtpResult?> {
         return withContext(dispatcherProvider.io){
             return@withContext remoteDataSource.verifyOtp(param)
         }
    }

}

