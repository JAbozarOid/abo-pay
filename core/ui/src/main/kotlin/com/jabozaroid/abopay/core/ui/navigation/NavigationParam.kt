package com.jabozaroid.abopay.core.ui.navigation

enum class NavigationParam {

    TRACKING_CODE,
    PHONE_NUMBER,
    OTP_TIME,
    WEB_VIEW_ITEM,
    MOBILE,
    SELECTED_OPERATOR,
    SIM_TYPE,
    OPERATOR_LOGO,
    PAYMENT_CONFIRMATION_MODEL,
    RECEIPT_MODEL,
    PAYMENT_MODEL;

    companion object {
        fun getByName(name: String): NavigationParam? {
            entries.forEach {
                if (name.equals(it.name, true)) return it
            }
            return null
        }
    }

}