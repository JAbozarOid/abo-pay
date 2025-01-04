package com.jabozaroid.abopay.core.network.dataSource.bill.mapper

import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryBranchInfoItemResult
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryItemResult
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryBranchInfoItemNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryItemNetworkResult
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryNetworkResult

fun BillInquiryNetworkResult.mapToBillInquiryResultDomain() =
    BillInquiryResult(

        billInquiryList = billInquiryList?.map {
            it?.mapToBillInquiryItemResultDomain()
        },

        branchInfoList = branchInfoList?.map {
            it?.mapToBillInquiryBranchInfoItemResultDomain()
        },

        isNotificationEnabled = isNotificationEnabled,

        isInBillList = isInBillList,
    )

fun BillInquiryItemNetworkResult.mapToBillInquiryItemResultDomain() =
    BillInquiryItemResult(
        amount = amount,
        billId = billId,
        payId = payId,
        date = date,
        termType = termType,
        termTypeDesc = termTypeDesc,
        payable = payable,
        amountDescription = amountDescription,
        isChecked = isChecked,
        billType = billType,
        totalAmount = totalAmount,
    )

fun BillInquiryBranchInfoItemNetworkResult.mapToBillInquiryBranchInfoItemResultDomain() =
    BillInquiryBranchInfoItemResult(
        key = key,
        value = value,
        name = name,
    )
