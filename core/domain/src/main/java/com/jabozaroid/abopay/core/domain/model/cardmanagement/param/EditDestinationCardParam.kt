package com.jabozaroid.abopay.core.domain.model.cardmanagement.param

data class EditDestinationCardParam(
    val pan: String? = null,
    val title: String? = null,
    val isDeleted: Boolean? = null,
)