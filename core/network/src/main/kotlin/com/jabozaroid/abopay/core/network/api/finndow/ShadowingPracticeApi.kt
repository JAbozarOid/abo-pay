package com.jabozaroid.abopay.core.network.api.finndow

import com.jabozaroid.abopay.core.domain.model.finndow.param.ShadowingPracticeParam
import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.finndow.ShadowingPracticeNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ShadowingPracticeApi {

    @POST("api/generate-exercise")
    suspend fun getShadowingPractice(
        @Body param: ShadowingPracticeParam,
    ): Response<ResSuccess<ShadowingPracticeNetworkResult>>

}