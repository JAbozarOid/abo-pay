package com.jabozaroid.abopay.core.network.helper

import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.ErrorData
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.AboPayServerError
import retrofit2.Response

suspend fun <T> execute(request: suspend () -> Response<T>): AboPayResult<T> {

    return try {
        val response = request()
        if (response.isSuccessful && response.body() != null) {
            AboPayResult.AboPaySuccess(response.body()!!)
        } else {
            var apiError: AboPayServerError

            val responseBody = response.errorBody()?.string()
            val gson = Gson()
            apiError = gson.fromJson(responseBody, AboPayServerError::class.java)
            if (apiError.error.code == -1) {
                apiError =
                    AboPayServerError(ErrorData(code = response.code(), message = response.message()))
            }
            AboPayResult.AboPayApiError(apiError)
        }
    } catch (ex: Exception) {
        AboPayResult.AboPayException(ThrowableParser.parse(ex))
    }
}