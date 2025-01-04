package com.jabozaroid.abopay.core.designsystem.component.model

import androidx.annotation.StringRes

/**
 * Created on 25,December,2024
 */

data class MobileNumberUiModel(
    val value: String = "",
    @StringRes val errorMessage: Int? = null,
    val errorStatus: Boolean = false
)

data class PhoneNumberItem(
    val phoneNumber: String,
    val ownerPhoneNumber: String,
    val icon : Int
)