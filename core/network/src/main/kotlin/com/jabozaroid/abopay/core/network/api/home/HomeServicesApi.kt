package com.jabozaroid.abopay.core.network.api.home

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.home.HomeServiceNetWorkResult
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created on 31,August,2024
 */
interface HomeServicesApi {

    @GET("and/api/v1/AppManagementAdaptor/GetAllCategory")
    suspend fun getHomeService(): Response<ResSuccess<HomeServiceNetWorkResult>>

}