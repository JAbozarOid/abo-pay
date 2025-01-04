package com.jabozaroid.abopay.core.network.api.auth


import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.auth.LoginOtpNetworkParam
import com.jabozaroid.abopay.core.network.model.auth.LoginOtpNetworkResult
import com.jabozaroid.abopay.core.network.model.auth.VerifyLoginOtpNetworkParam
import com.jabozaroid.abopay.core.network.model.auth.VerifyLoginOtpNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthenticationApi {
    @POST("and/api/v1/KeycloakAPI/RegisterLevel1")
    suspend fun getOtp(@Body body: LoginOtpNetworkParam): Response<ResSuccess<LoginOtpNetworkResult>>

    @POST("and/api/v1/KeycloakAPI/VerifyOTP")
    suspend fun verifyOtp(@Body body: VerifyLoginOtpNetworkParam): Response<ResSuccess<VerifyLoginOtpNetworkResult>>


}