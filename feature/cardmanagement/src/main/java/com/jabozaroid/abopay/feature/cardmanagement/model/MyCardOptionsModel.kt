package com.jabozaroid.abopay.feature.cardmanagement.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created on 25,November,2024
 */

data class MyCardOptionsModel(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val subtitle: Int?,
    @DrawableRes val icon: Int,
    val isEnabled: Boolean,
)
