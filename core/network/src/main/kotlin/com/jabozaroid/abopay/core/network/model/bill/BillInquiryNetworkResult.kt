package com.jabozaroid.abopay.core.network.model.bill

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BillInquiryNetworkResult(
    @SerializedName("bills")
    val billInquiryList: List<BillInquiryItemNetworkResult?>? = null,
    @SerializedName("branchInfo")
    val branchInfoList: List<BillInquiryBranchInfoItemNetworkResult?>? = null,
    val isNotificationEnabled: Boolean? = null,
    val isInBillList: Boolean? = null,
    val wage: Int? = null,
)

data class BillInquiryItemNetworkResult(
    var amount: BigDecimal? = null,
    var totalAmount: BigDecimal? = null,
    var billId: String? = null,
    var payId: String? = null,
    var date: String? = null,
    var termType: Int = 0,
    var termTypeDesc: String? = null,
    @SerializedName("amountDesc")
    var amountDescription: String? = null,
    var payable: Boolean = false,
    var isChecked: Boolean = false,
    var billType: Int = 0,
)

data class BillInquiryBranchInfoItemNetworkResult(
    val key: String? = null,
    val value: String? = null,
    val name: String? = null,
)


