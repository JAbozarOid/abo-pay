package com.jabozaroid.abopay.core.network.dataSource.payment.mapper

import com.jabozaroid.abopay.core.domain.model.payment.KeyValue
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import com.jabozaroid.abopay.core.network.model.payment.NetworkKeyValue
import com.jabozaroid.abopay.core.network.model.payment.PaymentNetworkParam
import com.jabozaroid.abopay.core.network.model.payment.PaymentNetworkResult

/**
 *  Created on 12/21/2024
 **/


fun PaymentNetworkResult.mapToDomainModel() : PaymentResult {
    return PaymentResult(
        items = this.items.map {
            it.mapToDomainModel()
        }
    )
}

fun PaymentRequestParam.mapToNetworkModel() : PaymentNetworkParam {
    return PaymentNetworkParam(
        paymentType = this.paymentType,
        items = this.items.map {
            it.mapToNetworkModel()
        }
    )
}


fun NetworkKeyValue.mapToDomainModel() : KeyValue {
    return KeyValue(
        key = this.key,
        value = this.value
    )
}

fun KeyValue.mapToNetworkModel() : NetworkKeyValue {
    return NetworkKeyValue(
        key = this.key,
        value = this.value
    )
}