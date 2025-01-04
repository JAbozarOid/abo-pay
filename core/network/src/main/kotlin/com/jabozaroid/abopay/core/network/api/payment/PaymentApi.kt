package com.jabozaroid.abopay.core.network.api.payment

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.payment.PaymentNetworkResult
import com.jabozaroid.abopay.core.network.model.payment.PaymentNetworkParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created on 12/21/2024 
 **/

interface PaymentApi {


    @POST("mock/PayRequest")
    suspend fun pay(@Body param : PaymentNetworkParam) : Response<ResSuccess<PaymentNetworkResult>>
}