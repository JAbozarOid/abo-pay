package com.jabozaroid.abopay.feature.kahroba.main.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.CardInformationUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

data class KahrobaUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val addCardBottomSheetUiModel: AddCardBottomSheetUiModel = AddCardBottomSheetUiModel(),
    val sourceCardList: List<Card> = listOf(
        Card(
            ownerName = "محمد حسینی",
            number = "6037997175607630",
            token = "1",
            cvv2 = CVV2(number = "456"),
            month = Month(number = "09"),
            year = Year("1409"),
            bankName = R.string.melli_bank,
            colorUp = R.color.melli_up,
            colorDown = R.color.melli_Down,
            icon = R.drawable.card_icon_white_melli,
            isDefault = true,
            kahrobaIsActiveWIthOtp = true
        ),
        Card(
            ownerName = "ابوذر رقیب دوست",
            number = "5892101502849876",
            token = "2",
            cvv2 = CVV2(number = "14587"),
            month = Month(number = "02"),
            year = Year("1406"),
            bankName = R.string.sepah_bank,
            colorUp = R.color.sepah_up,
            colorDown = R.color.sepah_dwon,
            icon = R.drawable.card_icon_color_sepah,
            kahrobaIsActiveWIthOtp = true

        ),
        Card(
            ownerName = "عطیه فریدونی",
            number = "6104338921525206",
            token = "3",
            cvv2 = CVV2(number = "963"),
            month = Month(number = "10"),
            year = Year("1403"),
            bankName = R.string.mellat_bank,
            colorUp = R.color.melat_up,
            colorDown = R.color.melat_down,
            icon = R.drawable.card_icon_color_mellat,
            kahrobaIsActiveWIthOtp = true

        ),
        Card(
            ownerName = "ساناز رمضان",
            number = "6037998284328142",
            token = "4",
            cvv2 = CVV2(number = "6598"),
            month = Month(number = "11"),
            year = Year("1490"),
            bankName = R.string.melli_bank,
            colorUp = R.color.melli_up,
            colorDown = R.color.melli_Down,
            icon = R.drawable.card_icon_white_melli,
            kahrobaIsActiveWIthOtp = true

        )
    ),
    val selectedCard: CardInformationUiModel = CardInformationUiModel(),
    val supportingBankList: List<SupportingBank> = listOf(
        SupportingBank(0, R.drawable.card_icon_color_melli),
        SupportingBank(1, R.drawable.card_icon_color_pasargad),
        SupportingBank(2, R.drawable.card_icon_color_eghtesad),
        SupportingBank(3, R.drawable.card_icon_color_maskan),
        SupportingBank(4, R.drawable.card_icon_color_mehr),
        SupportingBank(5, R.drawable.card_icon_color_shahr),
    ),
    val helperBottomSheet: HelperBottomSheet = HelperBottomSheet(),
    val scanCardVisibility: Boolean = true,
    val confirmOtpBottomSheet: ConfirmOtpBottomSheet = ConfirmOtpBottomSheet(),
) : IViewState {
    fun isVerifySubmitButtonEnable(): Boolean {
        return selectedCard.otpModel.errorMessage == null &&
                selectedCard.otpModel.otpCode.isNotBlank() && selectedCard.otpModel.otpCode.length == 6
    }
}

data class ConfirmOtpBottomSheet(
    val isVisible: Boolean = false,
    val errorMessage: String = "",
)

data class AddCardBottomSheetUiModel(
    val cardInformationUiModel: CardInformationUiModel = CardInformationUiModel(),
    val metaData: MetaData = MetaData(),
    val isBottomSheetVisible: Boolean = false,
)

data class HelperBottomSheet(
    val isVisible: Boolean = false,
    val errorMessage: String = "",
    val items: List<HelperItem> = listOf(),
)

data class HelperItem(
    val index: Int = 0,
    val description: String = "",
)


data class MetaData(
    val amount: Amount = Amount(),
    val description: Description = Description(),
    val saveDestinationCard: Boolean = true,
)

data class Amount(
    val value: String = "",
    @StringRes val errorMessage: Int? = null,
)

data class Description(
    val value: String = "",
    val errorMessage: String = "",
)

data class SupportingBank(
    val index: Int,
    val logo: Int,
)
