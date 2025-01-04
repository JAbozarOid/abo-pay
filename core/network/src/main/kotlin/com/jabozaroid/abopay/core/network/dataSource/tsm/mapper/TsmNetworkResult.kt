package com.jabozaroid.abopay.core.network.dataSource.tsm.mapper

import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainParam
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResponse
import com.jabozaroid.abopay.core.domain.model.tsm.TsmDomainResult
import com.jabozaroid.abopay.core.network.model.tsm.TsmNetworkParam
import com.jabozaroid.abopay.core.network.model.tsm.TsmNetworkResult

fun TsmNetworkResult.mapToTsmDomainModel(): TsmDomainResult {
    return TsmDomainResult().run {
        copy(this.tsmResponse)
    }
}

fun TsmNetworkResponse.mapToTsmDomainResponse(): TsmDomainResponse {
    return TsmDomainResponse().run {
        copy(
            trackingNumber = trackingNumber,
            transactionId = transactionId,
            registrationDate = registrationDate,
            registrationAddress = registrationAddress
        )
    }
}

data class TsmNetworkResponse(
    val trackingNumber: String,
    val transactionId: String,
    val registrationDate: Long,
    val registrationAddress: String,
)

fun TsmDomainParam.mapToTsmNetworkParam() = TsmNetworkParam(
    patternType = patternType
)