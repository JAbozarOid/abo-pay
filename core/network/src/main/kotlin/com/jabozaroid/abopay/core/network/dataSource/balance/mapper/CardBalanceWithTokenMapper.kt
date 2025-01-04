package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.CardBalanceWithTokenParam
import com.jabozaroid.abopay.core.network.model.balance.CardBalanceWithTokenNetworkParam

fun CardBalanceWithTokenParam.mapToNetwork() =
    CardBalanceWithTokenNetworkParam(
        pin = pin,
        cvv2 = cvv2,
        token = token,
        ExpireDate = ExpireDate
    )