package com.jabozaroid.abopay.feature.bill.model

import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentParam
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult
import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface BillAction : IAction {

    data object GetBillTypes : BillAction

    data class AddBill(val param: AddBillParam) : BillAction

    data class EditeBill(val editedBill: AddBillParam) : BillAction

    data class GetBillInquiry(val param: BillInquiryParam) : BillAction

    data object GetFrequentBill : BillAction

    data class ShowAddBillBottomSheet(val billType: BillTypeResult) : BillAction

    data object CloseAddBillBottomSheet : BillAction

    data class ConfirmPaymentID(val param: BillPaymentParam) : BillAction

    data class ShowInquiryBottomSheet(val param: BillInquiryParam): BillAction

    data object HideInquiryBottomSheet: BillAction

    data class ShowRemoveBottomSheet(val item: FrequentUiModel) : BillAction

    data object HideRemoveBottomSheet : BillAction

    data class ShowEditeBottomSheet(val item: FrequentUiModel) : BillAction

    data object HideEditeBottomSheet : BillAction

    data object ShowPaymentInputBottomSheet : BillAction

    data object HidePaymentInputBottomSheet : BillAction

    //    data class ShowPaymentBottomSheet(val param:BillPaymentParam):BillAction
    data object HidePaymentBottomSheet : BillAction
    data class DeleteFrequentBill(val item: FrequentUiModel) : BillAction

//    data object ConfirmEditeBill:BillAction

    data class ValidateBillIdData(val billId: String) : BillAction

    data object NavigateUp : BillAction

    data object NavigateToPayment : BillAction

}
