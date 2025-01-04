package com.jabozaroid.abopay.core.domain.model.charge.param.favourite

import com.google.gson.annotations.SerializedName

data class DeleteFavouriteChargeNumberParam(
    @SerializedName("PhoneNumber")
    val phoneNumber: String
)
