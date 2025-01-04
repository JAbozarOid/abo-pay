package com.jabozaroid.abopay.core.domain.model.tsm

import com.google.gson.annotations.SerializedName

data class TsmDomainParam(
    @SerializedName("PatternType")
    val patternType: Int,
)
