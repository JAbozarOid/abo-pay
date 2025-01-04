package com.jabozaroid.abopay.core.network.model.cardmanagement

import com.google.gson.annotations.SerializedName

data class AddSourceCardNetworkResult(
    val responseCode: Int? = null,
    val responseMessage: String? = null,
    val status: Int? = null,
    @SerializedName("succssed")
    val success: Boolean? = null,
    val token: String? = null,
)