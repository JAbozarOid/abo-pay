package com.jabozaroid.abopay.core.network.interceptor

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

private const val AUTHORIZATION = "Authorization"

class AuthenticationInterceptor @Inject constructor(
    private val sharedPrefStorage: SharedPrefStorage,
) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val authentication = request.header(AUTHORIZATION)
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        if (authentication.equals("none", ignoreCase = true)) {
            requestBuilder.removeHeader(AUTHORIZATION)
        } else if (authentication == null) {
            val token = getToken()
            requestBuilder.addHeader(AUTHORIZATION, "Bearer ${token?.trim()}")
        }

        val requestId = UUID.randomUUID().toString()
        requestBuilder.addHeader("Request-Id", requestId)

        request = requestBuilder.build()
        return chain.proceed(request)
    }


    private fun getToken(): String? {
        var token: String?
        runBlocking {
            token = sharedPrefStorage.getString(StorageKey.TOKEN)
        }
        return token

//        "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ4TjRsQUF2aHFlZUZKanVySjlNV2NsZ3c1NWF3Ujl3YS1YdjRiWG9DNmZnIn0.eyJleHAiOjE3MzMwNDg0NjIsImlhdCI6MTcyODcyODQ2MiwianRpIjoiYzdiOGRkZWItNTdjYS00ODczLWFlYmItOGY2MDYwODJlYmEyIiwiaXNzIjoiaHR0cDovLzE3Mi4yNC4zNC4xMTQ6ODA4MC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImEwZmY2ZGYyLWUxMjgtNGRiOS1hM2FmLTViMjlmYjRkZjJkMSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im15U3RhdGlvbiIsInNlc3Npb25fc3RhdGUiOiI2NjViYTE5NS1kYWM3LTRlZGMtYTY3MS1mMzc2NTIyOTgzMTciLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIi8qIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiI2NjViYTE5NS1kYWM3LTRlZGMtYTY3MS1mMzc2NTIyOTgzMTciLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6IjA5MTU4ODcxMjE1In0.Wrd0Pq7YBDHl3RXq80eCJ5UwAZRkuXV6LM9XVMl3UGMHstyFC3Yd75vr0mHs3_l2Q3dw-DUFMabSgRYThOhlSw8_ERFbziEIyE3wwlWTvVWFByirc3ypsP_lFovJ-Wzh5_5VAebT3Nlmx-bXhDldLOuX1pkyrbcR5RqD9fvJ6-qaFgAZNt_RdI1EIuu8zVXaFxTFoHnAlYAL0XPa-v9ku4Mdc0ARdvWE-HJVhQcLlTbQO7R2aHzue1XiQ3LmgIyHOiWyiJGMbivh1IdlKXa0teASSEXYFSCFguFjJO-ilFtLUPrLom4p9pu2uMc867IUy8FjZGGY6i3nmw5uKS8skw"
    }
}
