package com.jabozaroid.abopay.core.analytics.tracker

import android.util.Log
import com.google.gson.Gson
import com.jabozaroid.abopay.core.analytics.tracker.enum.TrackersEvent

/**
 * Created on 11,August,2024
 */


private const val TAG = "TRACKER"
private const val ADDING_TRACKER_ERROR = "ADDING TRACKER ERROR !!"
private const val INITIALIZING_ERROR = "INITIALIZING TRACKER SET ERROR !!"
private const val SENDING_EVENT_ERROR = "SENDING EVENT ERROR : "
private const val SENDING_ERROR_ERROR = "SENDING ERROR ERROR : "
private const val ACTIVITY_RESUME_ERROR = "ACTIVITY RESUME ERROR : "

object TrackerManager {

    private val trackerSet: HashSet<Tracker> = hashSetOf()


    private val gson = Gson()

    //TODO It should be uncommented after flavor implementation
//    private val couldConnectToTrackerServer = !BuildConfig.FLAVOR.contains("Dev")

    @Synchronized
    fun addTrackers(trackers: Set<Tracker>) {
        try {
            trackers.forEach {
                if (!trackerSet.contains(it))
                    trackerSet.add(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w(TAG, ADDING_TRACKER_ERROR)
        }
    }

    fun onEvent(event: TrackersEvent, data: String? = null) {
        onEvent(event.toString(), data)
    }

    fun onEvent(event: String, data: String? = null) {
        //TODO It should be uncommented after flavor implementation
//        if (!couldConnectToTrackerServer) {
//            Log.i(TAG, "ON EVENT -> $event")
//            return
//        }
        if (trackerSet.isNotEmpty()) {
            for (tracker in trackerSet) {
                if (tracker.isEnable) try {
                    tracker.submitEvent(event, data)
                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "$SENDING_EVENT_ERROR $event")
                }
            }
        }
    }

    @Synchronized
    fun initialize() {
        if (trackerSet.isNotEmpty()) {
            try {
                for (tracker in trackerSet)
                    if (tracker.isEnable)
                        tracker.init()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.w(TAG, INITIALIZING_ERROR)
            }

        }
    }


    fun onInstallReceiver() {
        if (trackerSet.isNotEmpty()) {
            for (tracker in trackerSet) {
                println(tracker.isEnable)
                if (tracker.isEnable) try {
                    tracker.onInstallReceiver()

                } catch (e: Exception) {
                }
            }
        }
    }


    fun onError(errorName: String, data: TrackerErrorData? = null) {
        onError(errorName, gson.toJson(data))
    }


    fun onError(errorName: String, data: String? = null) {
        //TODO It should be uncommented after flavor implementation
//        if (!couldConnectToTrackerServer) {
//            Log.i(TAG, "ON ERROR -> $errorName : $data")
//            return
//        }
        if (trackerSet.isNotEmpty()) {
            for (tracker in trackerSet) {
                if (tracker.isEnable) try {
                    tracker.onError(errorName, data)
                } catch (e: Exception) {
                    Log.e(TAG, "$SENDING_ERROR_ERROR $errorName : $data")
                }
            }
        }
    }


    fun onError(errorName: TrackersEvent, data: TrackerErrorData? = null) {
        onError(errorName.toString(), gson.toJson(data))
    }

    fun activityCreated(activity: String) {

        //TODO It should be uncommented after flavor implementation
//        if (!couldConnectToTrackerServer) {
//            Log.i(TAG, "ON EVENT -> $activity")
//            return
//        }

        if (trackerSet.isNotEmpty()) {
            trackerSet.forEach {
                if (it.isEnable) try {
                    it.activityCreated(activity)
                } catch (e: Exception) {
                    Log.e(TAG, "$ACTIVITY_RESUME_ERROR activity : $activity")
                }
            }
        }
    }


}

