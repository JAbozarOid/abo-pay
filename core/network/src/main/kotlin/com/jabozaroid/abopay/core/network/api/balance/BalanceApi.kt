package com.jabozaroid.abopay.core.network.api.balance

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.balance.BalanceNetworkResult
import com.jabozaroid.abopay.core.network.model.balance.BalanceVatNetworkResult
import com.jabozaroid.abopay.core.network.model.balance.CardBalanceWithPanNetworkParam
import com.jabozaroid.abopay.core.network.model.balance.CardBalanceWithTokenNetworkParam
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpNetworkResult
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpWithPanNetworkParam
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpWithTokenNetworkParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BalanceApi {

    @POST("and/api/v1/IvaCardsAPI/CardBalanceWithPan")
    suspend fun getCardBalanceWithPan(@Body param: CardBalanceWithPanNetworkParam): Response<ResSuccess<BalanceNetworkResult>>

    @POST("and/api/v1/SadadPSPHarimAdaptor/HarimRequestOptWithPan")
    suspend fun getHarimOtpWithPan(@Body param: HarimOtpWithPanNetworkParam): Response<ResSuccess<HarimOtpNetworkResult>>

    @POST("and/api/v1/SadadPSPHarimAdaptor/HarimRequestOptWithToken")
    suspend fun getHarimOtpWithToken(@Body param: HarimOtpWithTokenNetworkParam): Response<ResSuccess<HarimOtpNetworkResult>>

    @POST("and/api/v1/IvaCardsAPI/CardBalanceWithToken")
    suspend fun getCardBalanceWithToken(@Body param: CardBalanceWithTokenNetworkParam): Response<ResSuccess<BalanceNetworkResult>>

    @GET("and/api/v1/IvaCardsAPI/GetBalanceWage")
    suspend fun getBalanceVat(): Response<ResSuccess<BalanceVatNetworkResult>>

}