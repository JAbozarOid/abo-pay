package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class HarimOtpWithTokenNetworkParam(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("cardMedia")
    val cardMedia: CardMediaTokenNetwork? = CardMediaTokenNetwork(),
    @SerializedName("requestType")
    val requestType: Int? = null,
    @SerializedName("PhoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("UserSubjectId")
    val userSubjectId: Int? = null
)


data class CardMediaTokenNetwork(
    @SerializedName("Token")
    val token: String? = null
)