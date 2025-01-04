package com.jabozaroid.abopay.feature.kahroba.nfc.model

import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.kahroba.main.model.HelperBottomSheet

data class NFCUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val card: Card = Card(
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
        isDefault = true
    ),
    val helperBottomSheet: HelperBottomSheet = HelperBottomSheet(),
) : IViewState