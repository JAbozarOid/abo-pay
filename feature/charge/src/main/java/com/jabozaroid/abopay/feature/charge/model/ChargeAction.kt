package com.jabozaroid.abopay.feature.charge.model

import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface ChargeAction : IAction {

    data object GetTopUpCharges : ChargeAction

    data class ClearOperatorStatusByPhoneEmpty(val index: Int?) : ChargeAction

    data class UserPhoneNumberValue(val phoneNumber: String) : ChargeAction

    data class DeleteUserPhoneNumber(val phoneNumber: String) : ChargeAction

    data class PhoneNumberErrorValue(val phoneNumber: String) : ChargeAction

    data class PhoneNumberErrorStatus(val phoneNumber: String) : ChargeAction

    data class FindOperatorByUserInput(
        val phoneNumber: String,
        val operatorList: List<IconItemUiModel>,
    ) : ChargeAction

    data class TopUpCharges(val operatorIndex: Int, val operatorList: List<IconItemUiModel>) :
        ChargeAction

    data object HideTopUpChargesBottomSheet : ChargeAction

    data class GetLatestPhoneNumberValue(val phoneNumber: String) : ChargeAction

    data object NavigateToPaymentScreenSuccessfully : ChargeAction

    data class SelectChargeAmount(val amountUiModel: AmountUiModel) : ChargeAction

    data object NavigateToPaymentScreenFailing : ChargeAction

    data object NavigateUp : ChargeAction

    data class SelectOperatorItem(val index: Int?) : ChargeAction

    data class FrequentRemoveIconClicked(val item: FrequentUiModel) :
        ChargeAction

    data object HideRemoveBottomSheet : ChargeAction

    data class RemoveFrequentItem(val item: FrequentUiModel) :
        ChargeAction
}