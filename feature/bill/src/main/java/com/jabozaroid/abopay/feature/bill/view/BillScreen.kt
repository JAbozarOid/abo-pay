package com.jabozaroid.abopay.feature.bill.view

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentParam
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.bill.model.BillAction
import com.jabozaroid.abopay.feature.bill.model.BillEvent
import com.jabozaroid.abopay.feature.bill.model.BillUiModel
import com.jabozaroid.abopay.feature.bill.view.component.BillHomeContent
import com.jabozaroid.abopay.feature.bill.viewmodel.BillViewModel

class BillScreen : BaseScreen<BillUiModel, BillAction, BillEvent>(
    name = "bill",
    route = ApplicationRoutes.billScreenRoute
) {

    @Composable
    override fun ViewModel(): BillViewModel = hiltViewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content(state: BillUiModel) {
        val viewModel = ViewModel()

        var editedBillName = remember {
            ""
        }

        var isVisibilityOverlay by remember {
            mutableStateOf(false)
        }
        Scaffold(content = {

            BillHomeContent(
                state,
                onItemClick = {
                    it?.let {
                        viewModel.process(BillAction.ShowAddBillBottomSheet(it))
                    }
                },
                onButtonInquiry = {
                    viewModel.process(BillAction.GetBillInquiry(it))
                },
                onDismissAddBillBottomSheet = {
                    viewModel.process(BillAction.CloseAddBillBottomSheet)
                },
                onConfirmButtonPaymentInputBottomSheet = { billId, paymentId ->
                    viewModel.process(BillAction.HidePaymentBottomSheet)
                    viewModel.process(
                        BillAction.ConfirmPaymentID(
                            BillPaymentParam(
                                billId = billId,
                                paymentId = paymentId
                            )
                        )
                    )
                },
                onDismissInquiryBottomSheet = {
                    viewModel.process(BillAction.HideInquiryBottomSheet)
                },
                onBackButtonClick = {
                    viewModel.process(BillAction.NavigateUp)
                },
                onValueChangeFirstTextFiles = {
                    viewModel.process(BillAction.ValidateBillIdData(it))
                },
                onFrequentRemoveIconClicked = {
                    viewModel.process(BillAction.ShowRemoveBottomSheet(it))
                },
                onHideRemoveBottomSheet = {
                    viewModel.process(BillAction.HideRemoveBottomSheet)
                },
                onFrequentEditIconClicked = {
                    viewModel.process(BillAction.ShowEditeBottomSheet(it))
                },
                onHideEditeBottomSheet = {
                    viewModel.process(BillAction.HideEditeBottomSheet)
                },
                onConfirmEditeBottomSheet = {
                    val frequentBillEdited = AddBillParam(
                        id = state.editeBottomSheet.item?.secondText,
                        type = state.editeBottomSheet.item?.id,
                        name = editedBillName
                    )
                    isVisibilityOverlay = false
                    viewModel.process(BillAction.EditeBill(frequentBillEdited))
                }, onPaymentFromInquiryBottomSheet = {
                    viewModel.process(BillAction.NavigateToPayment)
                },
                onFrequentItemClick = {
                    val inquiryParam = BillInquiryParam(
                        id = it.secondText,
                        type = it.id ?: -1,
                        name = it.firstText,
                        termType = null
                    )
                    viewModel.process(BillAction.ShowInquiryBottomSheet(inquiryParam))
                },
                onShowPaymentInputBottomSheet = {
                    viewModel.process(BillAction.ShowPaymentInputBottomSheet)
                }, onClosePaymentInputBottomSheet = {
                    viewModel.process(BillAction.HidePaymentInputBottomSheet)
                },
                onDisMissPaymentBillBottomSheet = {
                    viewModel.process(BillAction.HidePaymentBottomSheet)
                },
                onCancelButtonPaymentBillBottomSheet = {
                    viewModel.process(BillAction.HidePaymentBottomSheet)
                },
                onPaymentButtonPaymentBillBottomSheet = {
                    viewModel.process(BillAction.NavigateToPayment)
                },
                onDeleteFrequentBillDeleteBottomSheet = {
                    viewModel.process(BillAction.DeleteFrequentBill(it))
                },
                onValueChangeNameTetFiledEditeBottomSheet = {
                    editedBillName = it
                },
                isOverlayViewVisibility = isVisibilityOverlay
            )
        })
    }
}

