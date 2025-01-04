package com.jabozaroid.abopay.application

import android.app.Application
import com.jabozaroid.abopay.core.analytics.tracker.Tracker
import com.jabozaroid.abopay.core.analytics.tracker.TrackerManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var trackers: Set<@JvmSuppressWildcards Tracker>

    override fun onCreate() {
        super.onCreate()
        //initTrackers()
    }

    private fun initTrackers() {
        TrackerManager.addTrackers(trackers)
        TrackerManager.initialize()
    }

}