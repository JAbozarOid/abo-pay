package com.jabozaroid.abopay.core.network.model.tsm

import com.google.gson.annotations.SerializedName

data class TsmNetworkParam(
    @SerializedName("PatternType")
    val patternType: Int,
)
