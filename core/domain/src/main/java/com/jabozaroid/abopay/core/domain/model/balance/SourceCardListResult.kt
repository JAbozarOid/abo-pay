package com.jabozaroid.abopay.core.domain.model.balance

import androidx.annotation.StringRes

data class SourceCardListResult(
    var number: String? = null,
    val token: String = "",
    val isActiveToken: Boolean = false,
    val month: SourceCardMonth = SourceCardMonth(),
    val year: SourceCardYear = SourceCardYear(),
    val icon: Int? = null,
    val bankName: Int? = null,
    val colorUp: Int? = null,
    val colorDown: Int? = null,
    val isDefault: Boolean = false,
    var ownerName: String? = null,
    val cardValidationLogo: Int? = null,
    val defaultCardLogo: Int? = null,
)


data class SourceCardMonth(
    val number: String? = null,
    @StringRes val errorMessage: Int? = null,
)

data class SourceCardYear(
    val number: String? = null,
    @StringRes val errorMessage: Int? = null,
)
