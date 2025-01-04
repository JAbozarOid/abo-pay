package com.jabozaroid.abopay.feature.charge.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.MobileNumberUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.Item
import com.jabozaroid.abopay.core.ui.model.IViewState

data class ChargeUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val mobileNumberUiModel: MobileNumberUiModel = MobileNumberUiModel(),
    val operatorUiModels: List<IconItemUiModel> = listOf(),
    val frequentItems: MutableList<FrequentUiModel> = mutableListOf(),
    val userChargePhoneNumbers: List<Item> = listOf(),
    val operatorIndex: Int? = null,
    val topUpChargesBottomSheetUiModel: TopUpChargesBottomSheetUiModel = TopUpChargesBottomSheetUiModel(),
    val removeBottomSheetUiModel: RemoveBottomSheetUiModel = RemoveBottomSheetUiModel(),
) : IViewState {

    fun isContinueEnable(): Boolean {
        return operatorIndex != null && !mobileNumberUiModel.errorStatus && mobileNumberUiModel.value.isNotBlank()
    }

    fun getMobileNumberValue(): String {
        return mobileNumberUiModel.value
    }
}

data class TopUpChargesBottomSheetUiModel(
    val topUpAmounts: List<AmountUiModel> = listOf(),
    val isBottomSheetVisible: Boolean = false,
    val isChargeAmountSelect: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class RemoveBottomSheetUiModel(
    val isRemoveBottomSheetVisible: Boolean = false,
    val selectedFrequentItem: FrequentUiModel = FrequentUiModel()
)