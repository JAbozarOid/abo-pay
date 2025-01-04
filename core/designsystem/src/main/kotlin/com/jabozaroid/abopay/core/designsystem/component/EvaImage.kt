package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created on 07,October,2024
 */

@Composable
fun UnsafeImageApp(
    modifier: Modifier = Modifier,
    darkLogo: String? = "",
    lightLogo: String? = "",
    logoResource:Int? = null,
    errorLogo: String? = "",
    placeholder: Int = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_feature,
    contentDescription: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val imageLoader =
        ImageLoader.Builder(LocalContext.current).okHttpClient(createUnsafeOkHttpClient()).build()
    var painter : AsyncImagePainter? = null
    if (!darkLogo.isNullOrEmpty()||!lightLogo.isNullOrEmpty() ) {
         painter = if (isSystemInDarkTheme()) {
            rememberAsyncImagePainter(
                model = darkLogo,
                placeholder = painterResource(id = placeholder),
                imageLoader = imageLoader
            )
        } else {
            rememberAsyncImagePainter(
                model = lightLogo,
                placeholder = painterResource(id = placeholder),
                imageLoader = imageLoader
            )
        }
    }else{
        painter = rememberAsyncImagePainter(
            model = logoResource,
            placeholder = painterResource(id = placeholder),
            imageLoader = imageLoader
        )
    }
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

private fun createUnsafeOkHttpClient(
): OkHttpClient {
    val timeOutInSecond = 45L

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory

    val okHttpClientBuilder = OkHttpClient.Builder()

    okHttpClientBuilder
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _: String?, _: SSLSession? -> true }
        .writeTimeout(timeOutInSecond, TimeUnit.SECONDS)
        .readTimeout(timeOutInSecond, TimeUnit.SECONDS)
        .connectTimeout(timeOutInSecond, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 5, TimeUnit.SECONDS))
        .retryOnConnectionFailure(true)

    return okHttpClientBuilder.build()
}