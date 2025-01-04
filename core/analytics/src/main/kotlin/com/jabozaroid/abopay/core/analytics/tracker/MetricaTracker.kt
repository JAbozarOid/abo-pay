package com.jabozaroid.abopay.core.analytics.tracker

import android.app.Application
import android.content.Context
import com.jabozaroid.abopay.core.analytics.tracker.enum.TrackersEvent
import com.yandex.metrica.AppMetricaDeviceIDListener
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created on 11,August,2024
 */

private const val YANDEX_KEY = "25a05e8a-9d73-4716-bc26-3bfa9f6bdac1"


@Singleton
class MetricaTracker @Inject constructor() : Tracker {

    override var isEnable: Boolean = true

    @ApplicationContext
    @Inject
    lateinit var context: Context

    override fun init() {

        val config =
            YandexMetricaConfig.newConfigBuilder(YANDEX_KEY).build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(context, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(Application())
        YandexMetrica.setLocationTracking(false)
        YandexMetrica.requestAppMetricaDeviceID(object : AppMetricaDeviceIDListener {

            override fun onLoaded(s: String?) {

            }


            override fun onError(reason: AppMetricaDeviceIDListener.Reason) {

            }

        })
    }

    override fun onError(event: String, error: String?) {
        YandexMetrica.reportError(event, error)
    }

    override fun submitEvent(event: String, data: String?) {
        YandexMetrica.reportEvent(event, data)
    }

    override fun onInstallReceiver() {
        YandexMetrica.reportEvent(TrackersEvent.INSTALL_ABO_PAY.name)
    }

    override fun activityCreated(activityName: String) {
        submitEvent(activityName)
    }

}