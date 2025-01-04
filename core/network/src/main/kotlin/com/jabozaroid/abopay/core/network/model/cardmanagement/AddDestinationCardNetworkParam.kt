package com.jabozaroid.abopay.core.network.model.cardmanagement

import com.google.gson.annotations.SerializedName

data class AddDestinationCardNetworkParam(
    @SerializedName("pan")
    val pan: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("isDeleted")
    val isDeleted: Boolean? = null,
)