package com.jabozaroid.abopay.core.designsystem.component.model

import androidx.annotation.StringRes

data class CardInformationUiModel(
    val card: Card = Card(),
    val otpModel: OtpModel = OtpModel(),
) {
    fun enableBalanceConfirmButton(): Boolean {
        return !card.number.isNullOrBlank() &&
                !card.year.number.isNullOrBlank() &&
                !card.month.number.isNullOrBlank() &&
                card.cvv2.number.isNotEmpty() && card.cvv2.number.isNotBlank() &&
                card.number?.length == 16 &&
                otpModel.otpCode.isNotEmpty() && otpModel.otpCode.isNotBlank() &&
                card.errorMessage == null && card.year.errorMessage == null &&
                card.month.errorMessage == null && card.cvv2.errorMessage == null &&
                otpModel.errorMessage == null

    }

    fun enableCardToCardButton(): Boolean {
        return !card.year.number.isNullOrBlank() &&
                card.year.errorMessage == null &&
                !card.month.number.isNullOrBlank() &&
                card.month.errorMessage == null &&
                card.cvv2.number.isNotEmpty() && card.cvv2.number.isNotBlank() &&
                card.cvv2.errorMessage == null &&
                otpModel.otpCode.isNotEmpty() && otpModel.otpCode.isNotBlank() &&
                otpModel.errorMessage == null
    }

    fun enablePaymentButton(): Boolean {
        return !card.number.isNullOrBlank() &&
                !card.year.number.isNullOrBlank() &&
                !card.month.number.isNullOrBlank() &&
                card.cvv2.number.isNotEmpty() && card.cvv2.number.isNotBlank() &&
                card.number?.length == 16 &&
                otpModel.otpCode.isNotEmpty() && otpModel.otpCode.isNotBlank() &&
                card.errorMessage == null && card.year.errorMessage == null &&
                card.month.errorMessage == null && card.cvv2.errorMessage == null &&
                otpModel.errorMessage == null
    }

    fun enablePaymentButtonWithToken(): Boolean {
        return !card.number.isNullOrBlank() &&
                card.cvv2.number.isNotEmpty() && card.cvv2.number.isNotBlank() &&
                card.number?.length == 16 &&
                otpModel.otpCode.isNotEmpty() && otpModel.otpCode.isNotBlank() &&
                card.errorMessage == null && card.cvv2.errorMessage == null &&
                otpModel.errorMessage == null
    }

}

data class Card(
    var number: String? = null,
    val token: String = "",
    val isActiveToken: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val cvv2: CVV2 = CVV2(),
    val month: Month = Month(),
    val year: Year = Year(),
    val icon: Int? = null,
    val bankName: Int? = null,
    val colorUp: Int? = null,
    val colorDown: Int? = null,
    val isDefault: Boolean = false,
    var ownerName: String? = null,
    val cardValidationLogo: Int? = null,
    val defaultCardLogo: Int? = null,
    val kahrobaIsActiveWIthOtp: Boolean = false,
    val hasExpDate: Boolean = false,
) {

    fun enableAddUserCardButton(): Boolean {
        return !number.isNullOrBlank() &&
                !year.number.isNullOrBlank() &&
                year.errorMessage == null &&
                !month.number.isNullOrBlank() &&
                month.errorMessage == null &&
                number?.length == 16
    }

    fun enableKahrobaAddUserCardButton(): Boolean {
        return !number.isNullOrBlank() &&
                !year.number.isNullOrBlank() &&
                year.errorMessage == null &&
                !month.number.isNullOrBlank() &&
                month.errorMessage == null &&
                cvv2.number.isNotBlank() &&
                cvv2.errorMessage == null &&
                number?.length == 16
    }
}

data class Month(
    val number: String? = null,
    @StringRes val errorMessage: Int? = null,
)

data class Year(
    val number: String? = null,
    @StringRes val errorMessage: Int? = null,
)

data class CVV2(
    var number: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class OtpModel(
    var otpCode: String = "",
    var enableOtpRequest: Boolean = true,
    var timeLeft: String = "درخواست رمز پویا",
    @StringRes var errorMessage: Int? = null,
)
