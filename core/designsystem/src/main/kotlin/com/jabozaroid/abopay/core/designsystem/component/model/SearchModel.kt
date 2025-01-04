package com.jabozaroid.abopay.core.designsystem.component.model

/**
 *  Created on 8/31/2024
 **/



data class SearchItemModel(
    val id: String,
    val title: String,
    var subTitle: String,
    val icon: Int? ,
    val hasValue : Boolean =false
)