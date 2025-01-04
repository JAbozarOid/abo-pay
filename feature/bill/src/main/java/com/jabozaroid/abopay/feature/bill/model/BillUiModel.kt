package com.jabozaroid.abopay.feature.bill.model

import androidx.annotation.StringRes
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryUiModel
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentData
import com.jabozaroid.abopay.core.domain.model.bill.BillPaymentParam
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult
import com.jabozaroid.abopay.core.ui.model.IViewState

data class BillUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    val billTypeItems: List<IconItemUiModel> = listOf(),
    val frequentBills: MutableList<FrequentUiModel> = mutableListOf(),
    val billInquiry: List<BillInquiryUiModel> = listOf(),
    val billPaymentData: BillPaymentData? = null,
    val inquiryBottomSheet: InquiryBottomSheet = InquiryBottomSheet(),
    val paymentInputBottomSheet: PaymentInputBottomSheet = PaymentInputBottomSheet(),
    val addBillBottomSheet: AddBillBottomSheet = AddBillBottomSheet(),
    val editeBottomSheet: EditeBottomSheet = EditeBottomSheet(),
    val removeBottomSheet: RemoveBottomSheet = RemoveBottomSheet(),
    val paymentBottomSheet: PaymentBottomSheet = PaymentBottomSheet(),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),

    ) : IViewState


//data class ShowBillInquiryBottomSheet

data class EditeBottomSheet(
    val isVisible: Boolean = false,
    val item: FrequentUiModel? = null,
)

data class RemoveBottomSheet(
    val visible: Boolean = false,
    val item: FrequentUiModel = FrequentUiModel(),
)

data class InquiryBottomSheet(
    val isVisible: Boolean = false,
    val uiBillInquiry: BillInquiryResult = BillInquiryResult(),
)

data class AddBillBottomSheet(
    val isVisible: Boolean = false,
    val billType: BillTypeResult? = null,
    @StringRes val error: Int? = null,
)

data class PaymentInputBottomSheet(
    val isVisible: Boolean = false,
    val uiBillInquiry: List<BillInquiryUiModel> = listOf(),
    val billIdError:String = "",
    val paymentIdError:String = "",
    val error:String = ""
)

data class PaymentBottomSheet(
    val isVisible: Boolean = false,
    val uiBillPaymentParam: BillPaymentParam = BillPaymentParam(),
)

