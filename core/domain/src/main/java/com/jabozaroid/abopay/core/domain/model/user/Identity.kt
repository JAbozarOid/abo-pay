package com.jabozaroid.abopay.core.domain.model.user

import com.jabozaroid.abopay.core.domain.model.exception.BusinessException

data class Identity(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken
) {
    companion object {

        fun create(argument: Argument) =
            Identity(
                accessToken = AccessToken(argument.accessToken),
                refreshToken = RefreshToken(argument.refreshToken)
            )

        data class Argument(
            val accessToken: String,
            val refreshToken: String,
        )
    }

}

@JvmInline
value class AccessToken(val value: String) {

    init {
        emptyGuard()
    }

    private fun emptyGuard() {
        if (value.isEmpty()) throw BusinessException("empty access token")
    }

}

@JvmInline
value class RefreshToken(val value: String) {

    init {
        emptyGuard()
    }

    private fun emptyGuard() {
        if (value.isEmpty()) throw BusinessException("empty refresh token")
    }

}
