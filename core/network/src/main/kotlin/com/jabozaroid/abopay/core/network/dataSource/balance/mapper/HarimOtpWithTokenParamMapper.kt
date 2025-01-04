package com.jabozaroid.abopay.core.network.dataSource.balance.mapper

import com.jabozaroid.abopay.core.domain.model.balance.CardMediaToken
import com.jabozaroid.abopay.core.domain.model.balance.HarimOtpWithTokenParam
import com.jabozaroid.abopay.core.network.model.balance.CardMediaTokenNetwork
import com.jabozaroid.abopay.core.network.model.balance.HarimOtpWithTokenNetworkParam

fun HarimOtpWithTokenParam.mapToNetworkModel() =
    HarimOtpWithTokenNetworkParam(
        amount = amount,
        requestType = requestType,
        cardMedia =  cardMedia?.mapToNetworkModel()
    )


fun CardMediaToken.mapToNetworkModel()=
    CardMediaTokenNetwork(
        token = token
    )
