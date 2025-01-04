package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithPanParam
import com.jabozaroid.abopay.core.network.model.balance.CardBalanceWithPanNetworkParam

fun CardBalanceWithPanParam.mapToNetwork() =
    CardBalanceWithPanNetworkParam(
        pan = pan,
        pin = pin,
        cvv2 = cvv2,
        expireDate = expireDate
    )