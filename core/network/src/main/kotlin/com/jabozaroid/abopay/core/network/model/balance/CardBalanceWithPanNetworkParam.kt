package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class CardBalanceWithPanNetworkParam(

    @SerializedName("Cvv2")
    var cvv2: String? = null,
    @SerializedName("ExpireDate")
    var expireDate: String? = null,
    @SerializedName("Pin")
    var pin: String? = null,
    @SerializedName("UserSubjectId")
    var userSubjectId: Int? = null,
    @SerializedName("PhoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("pan")
    var pan: String? = null
)
