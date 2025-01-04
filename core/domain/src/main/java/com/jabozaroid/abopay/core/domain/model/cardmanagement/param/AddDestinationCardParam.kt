package com.jabozaroid.abopay.core.domain.model.cardmanagement.param

data class AddDestinationCardParam(
    val pan: String? = null,
    val title: String? = null,
    val isDeleted: Boolean? = null,
)