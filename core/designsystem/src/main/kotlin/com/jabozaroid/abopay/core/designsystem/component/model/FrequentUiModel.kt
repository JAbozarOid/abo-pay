package com.jabozaroid.abopay.core.designsystem.component.model

import androidx.compose.ui.graphics.Color

data class FrequentUiModel(
    val id: Int = 0,
    val startIcon: Int? = null,
    val endIcon: Int? = null,
    val endIconURL: String? = null,
    val endIconTint: Color? = null,
    val overlayIcon1: Int? = null,
    val overlayIcon2: Int? = null,
    val firstText: String = "",
    val secondText: String = "",
    val thirdText: String? = "",
    val overlayText1: String = "",
    val overlayText2: String = "",
    val isOverlayViewVisibility: Boolean = false,
    val billTypeName:String = ""
)
