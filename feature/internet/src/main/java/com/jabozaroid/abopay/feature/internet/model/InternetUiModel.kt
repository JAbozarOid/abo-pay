package com.jabozaroid.abopay.feature.internet.model

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.MobileNumberUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.charge.result.favourite.Item
import com.jabozaroid.abopay.core.ui.model.IViewState

data class InternetUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val internetCatalogList: Map<InternetDurationType, List<InternetCatalogUIModel>> = mapOf(),
    val mobileNumberUiModel: MobileNumberUiModel = MobileNumberUiModel(),
    val operatorIndex: Int? = null,
    val operatorUiModels: List<IconItemUiModel> = listOf(),
    val userInternetPhoneNumbers: List<Item> = listOf(),
    val frequentItems: MutableList<FrequentUiModel> = mutableListOf(),
    val removeBottomSheetUiModel: RemoveBottomSheetUiModel = RemoveBottomSheetUiModel(),
    val topUpInternetBottomSheetUiModel: TopUpInternetBottomSheetUiModel = TopUpInternetBottomSheetUiModel(),
    val selectedSimType: SimType = SimType.PREPAID,
    ) : IViewState {

    fun isContinueEnable(): Boolean {
        return !mobileNumberUiModel.errorStatus && mobileNumberUiModel.value.isNotBlank()
    }

    fun getMobileNumberValue(): String {
        return mobileNumberUiModel.value
    }

}

data class RemoveBottomSheetUiModel(
    val isRemoveBottomSheetVisible: Boolean = false,
    val selectedFrequentItem: FrequentUiModel = FrequentUiModel()
)

data class TopUpInternetBottomSheetUiModel(
    val isBottomSheetVisible: Boolean = false
)

enum class SimType(val title: String) {
    PREPAID("اعتباری"), POSTPAID("دائمی")
}
