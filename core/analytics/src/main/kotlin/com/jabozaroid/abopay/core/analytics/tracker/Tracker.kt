package com.jabozaroid.abopay.core.analytics.tracker

/**
 * Created on 11,August,2024
 */

interface Tracker {

    var isEnable: Boolean

    fun submitEvent(event: String, data: String? = null)
    fun onInstallReceiver()
    fun init()
    fun onError(event: String, error: String?)
    fun activityCreated(activityName: String)
}