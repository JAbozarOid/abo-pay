package com.jabozaroid.abopay.core.domain

sealed interface AboPayResult<out T> {

    data class AboPaySuccess<T>(
        val data: T,
    ) : AboPayResult<T>

    data class AboPayApiError<T>(val error: AboPayServerError) : AboPayResult<T>

    data class AboPayException(
        val aboPayExceptionMessage: AboPayExceptionMessage,
    ) : AboPayResult<Nothing>
}

fun <T> AboPayResult<T>.onAboPaySuccess(block: (T) -> Unit): AboPayResult<T> {
    if (this is AboPayResult.AboPaySuccess) {
        block(this.data)
    }
    return this
}

fun <T> AboPayResult<T>.onAboPayException(block: (AboPayExceptionMessage) -> Unit): AboPayResult<T> {
    if (this is AboPayResult.AboPayException) {
        block(this.aboPayExceptionMessage)
    }
    return this
}

fun <T> AboPayResult<T>.onAboPayApiError(block: (error: AboPayServerError) -> Unit): AboPayResult<T> {
    if (this is AboPayResult.AboPayApiError) {
        block(error)
    }
    return this
}

inline fun <T, H> AboPayResult<T>.map(block: (T) -> H): AboPayResult<H> =
    when (this) {
        is AboPayResult.AboPaySuccess -> AboPayResult.AboPaySuccess(block(data))
        is AboPayResult.AboPayApiError -> AboPayResult.AboPayApiError(error)
        is AboPayResult.AboPayException -> AboPayResult.AboPayException(aboPayExceptionMessage)
    }
