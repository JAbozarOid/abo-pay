package com.jabozaroid.abopay.core.designsystem.component.model

import com.jabozaroid.abopay.core.designsystem.R


data class FeatureUiModel(
    val id: String,
    val title: String,
    val logo: Int = R.drawable.ic_feature,
    val route: String? = null,
)
