package com.jabozaroid.abopay.core.network.dataSource.bill.mapper

import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.network.model.bill.BillInquiryNetworkParam

fun BillInquiryParam.mapToBillInquiryNetworkParam() =
    BillInquiryNetworkParam(
        id = id,
        name = name,
        type = type,
        termType = termType
    )