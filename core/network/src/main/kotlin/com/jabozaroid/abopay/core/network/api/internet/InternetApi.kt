package com.jabozaroid.abopay.core.network.api.internet

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.internet.TopUpInternetNetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created on 16,October,2024
 */

interface InternetApi {

    @GET("and/api/v1/IvaChargeApi/GetAllCatalog?categoryid=3")
    @Headers("application-name: Iva;PWA")
    suspend fun getTopUpCatalogInternet(): Response<ResSuccess<TopUpInternetNetworkResult>>

}