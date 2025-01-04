package com.jabozaroid.abopay.core.notification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jabozaroid.abopay.core.notification.util.provideDefaultNotificationBuilder
import com.jabozaroid.abopay.core.notification.util.provideNotificationManager
import com.jabozaroid.abopay.core.notification.util.provideSingleActionNotificationBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Created on 14,August,2024
 */

@AndroidEntryPoint
class AboPayFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    @Named("MainActivity")
    lateinit var mainActivity: Class<*>

    //TODO: This is for sending firebase token to the backend server
//    @Inject
//    lateinit var sendFirebaseToken: SendFirebaseToken

    private var job: Job? = null


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        job = CoroutineScope(Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }).launch {
            //TODO: Here we must send firebase token to the backend server
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = message.notification
        val bundle = dataToBundle(message.data)
        notification?.let {
            val notificationBody = notification.body
            val notificationTitle = notification.title
            val notificationInstance =
                provideSingleActionNotificationBuilder(
                    baseContext,
                    provideDefaultNotificationBuilder(baseContext),
                    extras = bundle,
                    mainActivity
                )
            notificationInstance.setContentTitle(notificationTitle)
            notificationInstance.setContentText(notificationBody)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            provideNotificationManager(baseContext)
                .notify(50, notificationInstance.build())
        }

    }

    private fun dataToBundle(map: Map<String, String>): Bundle {
        return Bundle().apply {
            map.forEach {
                this.putString(it.key, it.value)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}