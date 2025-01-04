package com.jabozaroid.abopay.core.network.model.c2c.inquiry

import com.google.gson.annotations.SerializedName

/**
 *  Created on 11/6/2024
 **/
data class C2CInquiryNetworkParam(
    @SerializedName("Amount")
    val amount: String?,

    @SerializedName("DestinationPan")
    val destinationPan: String?,

    @SerializedName("Token")
    val token: String?,

    @SerializedName("Description")
    val description: String?,

    @SerializedName("SourcePan")
    val sourcePan: String?

)
