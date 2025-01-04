package com.jabozaroid.abopay.core.domain.model.bill

import java.math.BigDecimal

data class BillInquiryResult(

    val wage: String? = null,
    val docUrl: String? = null,
    val billInquiryList: List<BillInquiryItemResult?>? = null,
    val branchInfoList: List<BillInquiryBranchInfoItemResult?>? = null,
    val isNotificationEnabled: Boolean? = null,
    val isInBillList: Boolean? = null,
)

data class BillInquiryItemResult(
    var amount: BigDecimal? = null,
    var billId: String? = null,
    var payId: String? = null,
    var date: String? = null,
    var termType: Int? = null,
    var termTypeDesc: String? = null,
    var payable: Boolean? = null,
    var amountDescription: String? = null,
    var isChecked: Boolean? = null,
    var billType: Int? = null,
    var totalAmount: BigDecimal? = null,
)

data class BillInquiryBranchInfoItemResult(
    val key: String? = null,
    val value: String? = null,
    val name: String? = null,
)


