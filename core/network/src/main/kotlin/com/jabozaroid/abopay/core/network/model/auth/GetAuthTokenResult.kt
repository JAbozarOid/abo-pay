package com.jabozaroid.abopay.core.network.model.auth

data class GetAuthTokenResult(
 val accessToken: String,
 val refreshToken: String
)
