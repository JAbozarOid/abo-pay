package com.jabozaroid.abopay.core.network.model.cardmanagement

import com.google.gson.annotations.SerializedName

data class MyCardsNetworkResult(
    @SerializedName("userCards")
    val myCards: List<MyCardsNetworkResultItem>,
)

data class MyCardsNetworkResultItem(
    @SerializedName("bankBin")
    val bankBin: String? = null,
    @SerializedName("bankNameFormat")
    val bankNameFormat: String? = null,
    @SerializedName("extraData")
    val extraData: String? = null,
    @SerializedName("hasExpDate")
    val hasExpDate: Boolean? = null,
    @SerializedName("hasTSM")
    val hasTSM: Boolean? = null,
    @SerializedName("isDefault")
    val isDefault: Boolean? = null,
    @SerializedName("primaryAccNo")
    val primaryAccNo: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("tokenExpDate")
    val tokenExpDate: String? = null
)