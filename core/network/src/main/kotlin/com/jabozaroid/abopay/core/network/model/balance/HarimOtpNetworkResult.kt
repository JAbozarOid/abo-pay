package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class HarimOtpNetworkResult(
    @SerializedName("transactionDateTime")
    val transactionDateTime: String? = null,
    @SerializedName("responseCode")
    val responseCode: Int? = null,
    @SerializedName("systemTrace")
    val systemTrace: Int? = null,
    @SerializedName("correlationId")
    val correlationId: String? = null,
    @SerializedName("message")
    val message: String? = null
)
