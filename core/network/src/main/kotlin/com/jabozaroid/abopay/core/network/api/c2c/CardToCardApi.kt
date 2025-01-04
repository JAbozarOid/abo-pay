package com.jabozaroid.abopay.core.network.api.c2c

import com.jabozaroid.abopay.core.network.helper.ResSuccess
import com.jabozaroid.abopay.core.network.model.c2c.inquiry.C2CInquiryNetworkParam
import com.jabozaroid.abopay.core.network.model.c2c.inquiry.C2CInquiryNetworkResult
import com.jabozaroid.abopay.core.network.model.c2c.transfer.C2CTransferNetworkParam
import com.jabozaroid.abopay.core.network.model.c2c.transfer.C2CTransferNetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created on 11/6/2024
 **/
interface CardToCardApi {

    @POST("and/api/V1/IvaCardServiceAdaptor/InquiryWithToken")
    suspend fun inquiryWithToken(@Body param: C2CInquiryNetworkParam): Response<ResSuccess<C2CInquiryNetworkResult>>

    @POST("and/api/V1/IvaCardServiceAdaptor/InquiryWithPan")
    suspend fun inquiryWithPan(@Body param: C2CInquiryNetworkParam): Response<ResSuccess<C2CInquiryNetworkResult>>

    @POST("and/api/V1/IvaCardServiceAdaptor/CardTransfer")
    suspend fun cardTransfer(@Body param: C2CTransferNetworkParam): Response<ResSuccess<C2CTransferNetworkResult>>

}