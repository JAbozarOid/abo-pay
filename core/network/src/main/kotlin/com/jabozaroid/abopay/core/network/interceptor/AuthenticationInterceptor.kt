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
        val token: String? = api_key

        /*runBlocking {
            token = sharedPrefStorage.getString(StorageKey.TOKEN)
        }*/
        return token

    }
}
