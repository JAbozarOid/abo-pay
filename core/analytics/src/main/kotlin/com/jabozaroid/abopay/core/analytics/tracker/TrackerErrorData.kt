package com.jabozaroid.abopay.core.analytics.tracker

/**
 * Created on 11,August,2024
 */

class TrackerErrorData {
    var appUrl: String? = null
    var errorBody: String? = null
    var contentType: String? = null
    var errorCode: String? = null

    override fun toString(): String {
        return "{errorBody : $errorBody}"
    }
}