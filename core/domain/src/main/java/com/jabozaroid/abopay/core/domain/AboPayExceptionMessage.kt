package com.jabozaroid.abopay.core.domain

import com.google.gson.annotations.SerializedName

data class AboPayExceptionMessage(
    val code: Int,
    @SerializedName("message") var text: String,
    var type: AppError = AppError.ServerError,
) : Exception() {
    constructor() : this(-1, "")
    constructor(msg: String) : this(-1, msg)
    constructor(msg: String, type: AppError) : this(-1, msg, AppError.NetworkError)
}