package com.jabozaroid.abopay.feature.internet.model

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface InternetAction : IAction {

    data object NavigateUp : InternetAction

    data object GetTopUpInternets : InternetAction

    data class UserPhoneNumberValue(val phoneNumber: String) : InternetAction

    data class DeleteUserPhoneNumber(val phoneNumber: String) : InternetAction

    data class PhoneNumberErrorValue(val phoneNumber: String) : InternetAction

    data class PhoneNumberErrorStatus(val phoneNumber: String) : InternetAction

    data class ClearOperatorStatusByPhoneEmpty(val index: Int?) : InternetAction

    data class FindOperatorByUserInput(
        val phoneNumber: String,
        val operatorList: List<IconItemUiModel>,
    ) : InternetAction

    data class GetLatestPhoneNumberValue(val phoneNumber: String) : InternetAction

    data class SelectOperatorItem(val index: Int?) : InternetAction

    data class FrequentRemoveIconClicked(val item: FrequentUiModel) :
        InternetAction

    data object HideRemoveBottomSheet : InternetAction

    data class RemoveFrequentItem(val item: FrequentUiModel) :
        InternetAction

    data class TopUpInternets(val operatorIndex: Int, val operatorList: List<IconItemUiModel>) :
        InternetAction

    data object HideTopUpInternetsBottomSheet : InternetAction

//    data object NavigateToPaymentScreenSuccessfully : InternetAction

    data class NavigateToInternetListScreen(val simType: SimType) : InternetAction

//    data class GetInternetList(val selectedOperator: String, val simType: String) : InternetAction

}