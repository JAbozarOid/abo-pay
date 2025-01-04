package com.jabozaroid.abopay.core.notification.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.createBitmap
import com.google.firebase.messaging.FirebaseMessaging
import com.jabozaroid.abopay.notification.R

/**
 * Created on 18,August,2024
 */

private const val NOTIFICATION_NAME_KEY = "pulse"
private const val NOTIFICATION_CHANNEL_ID_KEY = "pulse_channel_01"

fun initialFCM() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->

        if (!task.isSuccessful) {
            return@addOnCompleteListener
        }

        val token = task.result
        Log.d("FCMToken", "FCM Token: $token")
    }
}

fun checkNotificationPermission(context: Context): Boolean {
    val isPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }

    return isPermission
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getPermission(activity: AppCompatActivity, granted: (Boolean) -> Unit) {
    activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        granted(isGranted)
    }.launch(Manifest.permission.POST_NOTIFICATIONS)
}

fun initFirebaseMessagingTopics() {
//    FirebaseMessaging.getInstance().subscribeToTopic("IvaAllUsers")
//    FirebaseMessaging.getInstance().subscribeToTopic("IvaAndroidUsers")
}

fun createPendingIntent(
    context: Context,
    extras: Bundle? = null,
    requestCode: Int = 0,
    uri: Uri? = null,
    mainActivity: Class<*>
): PendingIntent {
    with(Intent(context, mainActivity)) {
        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.putExtras(extras ?: Bundle().apply {
            putString("Message", "Hello Application")
        })
        uri?.let {
            this.data = it
        }
        return PendingIntent.getActivity(
            context,
            1014,
            this,
            if (Build.VERSION.SDK_INT >= 23) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_ONE_SHOT
        )
    }

}

fun provideDefaultNotificationBuilder(context: Context): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_KEY)
        .setDefaults(Notification.DEFAULT_ALL)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setLargeIcon(context.vectorToBitmap(R.drawable.ic_eva))
        .setSmallIcon(R.drawable.ic_eva)
        .setLights(Color.YELLOW, 250, 3000)
        .setAutoCancel(false).setPriority(NotificationCompat.PRIORITY_MAX).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.setChannelId(NOTIFICATION_CHANNEL_ID_KEY)
            }
        }
}

fun provideSingleActionNotificationBuilder(
    context: Context, notificationCompatBuilder: NotificationCompat.Builder,
    extras: Bundle? = null,
    mainActivity: Class<*>
): NotificationCompat.Builder {
    return notificationCompatBuilder.setContentIntent(
        createPendingIntent(
            context,
            extras = extras,
            mainActivity = mainActivity
        )
    )
}

fun provideNotificationManager(context: Context): NotificationManagerCompat {
    val notificationManager = NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID_KEY, NOTIFICATION_NAME_KEY,
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        channel.setSound(ringtoneManager, audioAttributes)
        notificationManager.createNotificationChannel(channel)
    }
    return notificationManager
}

fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
    val drawable = AppCompatResources.getDrawable(this, drawableId) ?: return null
    val bitmap =
        createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            ?: return null

    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, canvas.width, canvas.height)

    drawable.draw(canvas)

    return bitmap
}