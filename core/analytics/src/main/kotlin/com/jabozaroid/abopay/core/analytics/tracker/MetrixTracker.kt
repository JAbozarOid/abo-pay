package com.jabozaroid.abopay.core.analytics.tracker

import android.content.Context
import com.jabozaroid.abopay.core.analytics.tracker.enum.TrackersEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.metrix.Metrix
import ir.metrix.UserIdListener
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created on 11,August,2024
 */

@Singleton
class MetrixTracker @Inject constructor() : Tracker {

    override var isEnable: Boolean = false

    @ApplicationContext
    @Inject
    lateinit var context: Context


    companion object {
        private var eventMap = hashMapOf(
            TrackersEvent.TRANSACTION to "oefjf",
            TrackersEvent.FIRST_TRANSACTION to "obhnd",
            TrackersEvent.REGISTER to "ezviy",
            TrackersEvent.OPEN to "jxrio"
        )
    }

    override fun submitEvent(event: String, data: String?) {
        if (event.isNotEmpty())
            if (eventMap.containsValue(event))
                Metrix.newEvent(event)
    }


    override fun onInstallReceiver() {
        submitEvent(eventMap[TrackersEvent.INSTALL_ABO_PAY_RECEIVER] ?: "")
    }

    override fun init() {
        try {
            Metrix.setUserIdListener(object : UserIdListener {
                override fun onUserIdReceived(userId: String) {
                    //TODO Save userId in storage
                }
            })
        } catch (_: Exception) {

        }
    }

    override fun onError(event: String, error: String?) {
        Metrix.newEvent(event)
    }

    override fun activityCreated(activityName: String) {
        submitEvent(activityName)
    }

}