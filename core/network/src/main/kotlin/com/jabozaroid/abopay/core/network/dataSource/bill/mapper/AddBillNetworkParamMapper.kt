package com.jabozaroid.abopay.core.network.dataSource.bill.mapper

import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.network.model.bill.AddBillNetworkParam

fun AddBillParam.mapToAddBillNetworkParam() =
    AddBillNetworkParam(
        id = id,
        type = type,
        name = name
    )