package com.jabozaroid.abopay.core.network.api.cardmanagement

import com.jabozaroid.abopay.core.domain.model.cardmanagement.param.DeleteSourceCardParam
import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddDestinationCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddSourceCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.AddSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.DeleteSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.DestinationCardsNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditDestinationCardNetworkParam
import com.jabozaroid.abopay.core.network.model.cardmanagement.EditSourceCardNetworkResult
import com.jabozaroid.abopay.core.network.model.cardmanagement.MyCardsNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CardManagementApi {

    @GET("/and/api/V1/CardManagementAdaptor/AllSourceCards")
    suspend fun getSourceCards(): Response<ResSuccess<MyCardsNetworkResult>>

    @POST("/and/api/V1/CardManagementAdaptor/RemoveSourceCard")
    suspend fun deleteSourceCard(
        @Body param: DeleteSourceCardParam,
    ): Response<ResSuccess<DeleteSourceCardNetworkResult>>

    @PUT("/and/api/V1/CardManagementAdaptor/UpdateSourceCard")
    suspend fun editSourceCard(
        @Body param: EditCardNetworkParam,
    ): Response<ResSuccess<EditSourceCardNetworkResult>>

    @POST("/and/api/V1/CardManagementAdaptor/AddSourceCard")
    suspend fun addSourceCard(
        @Body param: AddSourceCardNetworkParam,
    ): Response<ResSuccess<AddSourceCardNetworkResult>>

    @GET("/and/api/V1/CardManagementAdaptor/AllCards")
    suspend fun getDestinationCards(): Response<ResSuccess<DestinationCardsNetworkResult>>

    @DELETE("/and/api/V1/CardManagementAdaptor/RemoveCard")
    suspend fun deleteDestinationCard(
        @Query("id") id: Int,
    ): Response<ResSuccess<Boolean>>

    @POST("/and/api/V1/CardManagementAdaptor/AddCard")
    suspend fun addDestinationCard(
        @Body param: AddDestinationCardNetworkParam,
    ): Response<ResSuccess<Boolean>>

    @POST("/and/api/V1/CardManagementAdaptor/AddCard")
    suspend fun editDestinationCard(
        @Body param: EditDestinationCardNetworkParam,
    ): Response<ResSuccess<Boolean>>

}