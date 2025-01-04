package com.jabozaroid.abopay.core.analytics.tracker

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import com.google.firebase.analytics.ktx.logEvent
import com.jabozaroid.abopay.core.analytics.tracker.enum.BundleKey
import com.jabozaroid.abopay.core.common.infotype.enums.InfoType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created on 13,August,2024
 */

@Singleton
class FirebaseTracker @Inject constructor() : Tracker {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    lateinit var applicationInfo: Map<com.jabozaroid.abopay.core.common.infotype.enums.InfoType, @JvmSuppressWildcards String>

    override var isEnable: Boolean = true

    override fun init() {
        firebaseAnalytics.setUserProperty(
            com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_CODE.name,
            applicationInfo[com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_CODE]
        )
        firebaseAnalytics.setUserProperty(
            com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_NAME.name,
            applicationInfo[com.jabozaroid.abopay.core.common.infotype.enums.InfoType.VERSION_NAME]
        )
    }

    override fun onError(event: String, error: String?) {
        firebaseAnalytics.logEvent(event, if (error != null) Bundle().apply {
            putString(BundleKey.ERROR_DATA.name, error)
        } else null)
    }

    override fun submitEvent(event: String, data: String?) {
        val bundle = Bundle().apply {
            data?.let {
                putString(BundleKey.EVENT_DATA.toString(), it)
            }
        }
        firebaseAnalytics.logEvent(event, bundle)
    }

    override fun onInstallReceiver() {

    }

    override fun activityCreated(activityName: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(SCREEN_NAME, activityName)
            param(SCREEN_CLASS, activityName)
        }
    }
}