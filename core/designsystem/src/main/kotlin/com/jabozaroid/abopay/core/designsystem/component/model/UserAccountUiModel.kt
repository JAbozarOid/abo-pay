package com.jabozaroid.abopay.core.designsystem.component.model


data class UserAccountUiModel(
    val id: String,
    val name: String,
    val accountNumber: String,
    val iban: String,
    val balance: String? = null,
)