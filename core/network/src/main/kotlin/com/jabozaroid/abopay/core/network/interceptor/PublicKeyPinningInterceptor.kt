import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class PublicKeyPinningInterceptor @Inject constructor() :
    Interceptor {
//    private val eventHandler: InternalEventHandler = eventHandler

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: SSLPeerUnverifiedException) {
//            Trackers.onError(
//                TrackerEvent.ERROR_SSL_PINNING.toString(),
//                ExceptionHelper.getStackTrace(e)
//            )
//            eventHandler.post(InternalEvent.SSL_PINNING_ERROR)
            return getEmptyResponse(chain.request())
        }
    }

    private fun getEmptyResponse(request: Request): Response {
        val MIMEType = "text/*; charset=utf-8".toMediaTypeOrNull()
        val responseBody: ResponseBody = ResponseBody.create(
            MIMEType,
            "{\"error\":{\"code\":525,\"message\":\".خطا در دریافت اطلاعات، لطفا برای استفاده از این سرویس نرم افزار خود را به روز رسانی نمایید\",\"strCode\":\"\"}}"
        )
        return Response.Builder()
            .code(SSL_ERROR)
            .body(responseBody)
            .protocol(Protocol.HTTP_2)
            .request(request)
            .message("fakeResponse")
            .build()
    }

    companion object {
        const val SSL_ERROR: Int = 525
    }
}