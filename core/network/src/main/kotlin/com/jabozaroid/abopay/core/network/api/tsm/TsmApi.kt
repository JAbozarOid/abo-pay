package com.jabozaroid.abopay.core.network.api.tsm

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.tsm.TsmNetworkParam
import com.jabozaroid.abopay.core.network.model.tsm.TsmNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TsmApi {

    @POST("and/api/V1/IvaHubFanavaranServiceAdaptor/CardEnrollment")
    suspend fun cardEnrollment(@Body param: TsmNetworkParam): Response<ResSuccess<TsmNetworkResult>>

    @POST("and/api/V1/IvaHubFanavaranServiceAdaptor/Reactivaion")
    suspend fun appReactivation(@Body param: TsmNetworkParam): Response<ResSuccess<TsmNetworkResult>>
}