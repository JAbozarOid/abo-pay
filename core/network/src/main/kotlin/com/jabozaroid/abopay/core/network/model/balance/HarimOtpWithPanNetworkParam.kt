package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class HarimOtpWithPanNetworkParam(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("cardMedia")
    val cardMedia: CardMediaPanNetwork? = CardMediaPanNetwork(),
    @SerializedName("requestType")
    val requestType: Int? = null,
)


data class CardMediaPanNetwork(
    @SerializedName("pan")
    val pan: String? = null
)