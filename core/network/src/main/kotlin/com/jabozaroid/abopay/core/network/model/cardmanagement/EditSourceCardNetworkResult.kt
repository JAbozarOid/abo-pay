package com.jabozaroid.abopay.core.network.model.cardmanagement

import com.google.gson.annotations.SerializedName

data class EditSourceCardNetworkResult(
    @SerializedName("succssed")
    val success: Boolean
)