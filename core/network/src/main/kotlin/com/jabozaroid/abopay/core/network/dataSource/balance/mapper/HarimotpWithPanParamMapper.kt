package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.CardMediaPan
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithPanParam
import com.jabozaroid.abopay.core.network.model.balance.CardMediaPanNetwork
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpWithPanNetworkParam

fun HarimOtpWithPanParam.mapToNetworkModel() =
    HarimOtpWithPanNetworkParam(
        cardMedia = cardMediaPan?.mapToNetworkModel(),
        amount = amount,
        requestType = requestType
    )

fun CardMediaPan.mapToNetworkModel() =
    CardMediaPanNetwork(
        pan = pan,
    )