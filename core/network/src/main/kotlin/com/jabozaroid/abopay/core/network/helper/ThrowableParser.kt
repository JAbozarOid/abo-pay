package com.jabozaroid.abopay.core.network.helper

import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.AppError
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ThrowableParser {

    fun parse(exception: Exception): AboPayExceptionMessage {
        if (exception is ConnectException || exception.cause is ConnectException ||
            exception is ProtocolException
        ) return AboPayExceptionMessage("ارتباط با سرور برقرار نشد!", AppError.NetworkError)
        if (exception is SocketException ||
            exception is InterruptedIOException ||
            exception is UnknownHostException ||
            exception is EOFException ||
            exception is SSLException
        ) return AboPayExceptionMessage(
            "ارتباط با سرور برقرار نشد،لطفا اتصال اینترنت خود را بررسی نمایید!",
            AppError.NetworkError
        )

        if (exception is IOException) return AboPayExceptionMessage(
            "ارتباط با سرور برقرار نشد! مجددا تلاش نمایید.",
            AppError.NetworkError
        )

        if (exception is HttpException) {
            try {
                return parseHttpException(exception)
            } catch (ignored: Exception) {
                ignored.message
            }
        }
        return AboPayExceptionMessage(-1, "بروز خطا در اتصال")
    }

    private fun parseHttpException(throwable: HttpException): AboPayExceptionMessage {
        val response = throwable.response()
        val code = response?.code() ?: -1
        return try {
            Gson().fromJson(response?.errorBody()?.string(), AboPayExceptionMessage::class.java)
        } catch (e: Exception) {
            AboPayExceptionMessage(code, "بروز خطا در دریافت اطلاعات")
        }
    }
}