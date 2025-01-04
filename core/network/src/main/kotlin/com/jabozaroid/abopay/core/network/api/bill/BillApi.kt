package com.jabozaroid.abopay.core.network.api.bill

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.bill.AddBillNetworkParam
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryNetworkParam
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillsNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillsTypeNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BillApi {

    @GET("and/api/v1/IvaBillAPI/GetBillTypes")
    suspend fun getBillType(): Response<ResSuccess<BillsTypeNetworkResult>>

    @Headers("ClientId:AND")
    @POST("and/api/v1/IvaBillAPI/Inquiry")
    suspend fun getBillInquiry(
        @Body param: BillInquiryNetworkParam,
    ): Response<ResSuccess<BillInquiryNetworkResult>>

    @POST("and/api/v1/IvaBillAPI/AddBill")
    suspend fun addBill(@Body param: AddBillNetworkParam): Response<Unit>

    @GET("and/api/v1/IvaBillAPI/GetBillsByUser")
    suspend fun getBills(): Response<ResSuccess<BillsNetworkResult>>

    @PUT("and/api/v1/IvaBillAPI/UpdateBill")
    suspend fun updateBill(
        @Body param: AddBillNetworkParam
    ): Response<Unit>

    @DELETE("and/api/v1/IvaBillAPI/DeleteBill")
    suspend fun deleteBill(
        @Path("Id") billId: String,
        @Path("Type") billType: Int,
    ): Response<Unit>

}