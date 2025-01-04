package com.jabozaroid.abopay.core.network.model.bill

import com.google.gson.annotations.SerializedName


data class AddBillNetworkParam(
    @SerializedName("Id")
    var id: String? = null,
    @SerializedName("Type")
    var type: Int? = null,
    @SerializedName("Name")
    var name: String? = null,
)
