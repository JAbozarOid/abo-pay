package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class CardBalanceWithTokenNetworkParam(
    @SerializedName("Cvv2")
    val cvv2: String? = null,
    @SerializedName("ExpireDate")
    val ExpireDate: String? = null,
    @SerializedName("Pin")
    val pin: String? = null,
    @SerializedName("UserSubjectId")
    val userSubjectId: Int? = null,
    @SerializedName("PhoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("Token")
    val token: String? = null

)
