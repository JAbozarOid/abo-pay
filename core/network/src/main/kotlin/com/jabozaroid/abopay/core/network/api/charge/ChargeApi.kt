package com.jabozaroid.abopay.core.network.api.charge

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.charge.result.FavouriteChargeNumNetworkResult
import com.jabozaroid.abopay.core.network.model.charge.result.PinChargeNetworkResult
import com.jabozaroid.abopay.core.network.model.charge.result.TopUpChargeNetworkResult
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ChargeApi {

    @GET("and/api/v1/IvaChargeApi/GetAllFavoriteMobileNumber")
    suspend fun getFavouriteChargeNumbers(
        @QueryMap options: Map<String, String>,
    ): Response<ResSuccess<FavouriteChargeNumNetworkResult>>

    @DELETE("and/api/v1/IvaChargeApi/DeleteFavoriteMobileNumber")
    suspend fun deleteFavouriteChargeNumbers(
        @Query("phone") phoneNumber: String,
    ): Response<ResSuccess<Boolean>>

    @GET("and/api/v1/IvaChargeApi/GetAllTopUpCatalog")
    suspend fun getTopUpCharges(): Response<ResSuccess<TopUpChargeNetworkResult>>


    @GET("and/api/v3/charges/pin/mobile/catalog")
    suspend fun getPinCharges(): Response<ResSuccess<PinChargeNetworkResult>>
}