package com.jabozaroid.abopay.core.network.interceptor

import com.jabozaroid.abopay.core.network.helper.InfoType
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Information interceptor
 * this interceptor adds generic information into headers
 */

//<InfoType,String>)
class InformationInterceptor @Inject constructor(private val info: Map<InfoType, String>) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder =
            chain.request().newBuilder()
        for ((key, value) in info) {
            requestBuilder.addHeader(
                key.title, value
            )
        }
        return chain.proceed(requestBuilder.build())
    }

}