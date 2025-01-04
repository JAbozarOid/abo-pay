package com.jabozaroid.abopay.core.network.model.bill

import com.google.gson.annotations.SerializedName

data class BillInquiryNetworkParam(
    @SerializedName("Id")
    val id: String?,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("TermType")
    val termType: Int?,
)