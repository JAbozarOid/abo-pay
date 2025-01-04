package com.jabozaroid.abopay.core.network.model.balance

import com.google.gson.annotations.SerializedName

data class BalanceNetworkResult(
    @SerializedName("availableAmountSeparated")
    val availableAmountSeparated: String? = null,
    @SerializedName("amountSeparated")
    val amountSeparated: String? = null,
    @SerializedName("availableAmount")
    val availableAmount: String? = null,
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("maskedPan")
    val maskedPan: String? = null,
    @SerializedName("primaryAccNo")
    val primaryAccNo: String? = null,
    @SerializedName("issuerName")
    val issuerName: String? = null,
    @SerializedName("transactionDate")
    val transactionDate: String? = null,
    @SerializedName("trace")
    val trace: String? = null,
    @SerializedName("rrn")
    val rrn: String? = null,
    @SerializedName("point")
    val point: Int? = null

)
