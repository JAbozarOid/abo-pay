package com.jabozaroid.abopay.core.network.dataSource.c2c.mapper

import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.CardHolderInquiry
import com.jabozaroid.abopay.core.network.model.c2c.inquiry.C2CInquiryNetworkParam
import com.jabozaroid.abopay.core.network.model.c2c.inquiry.C2CInquiryNetworkResult
import com.jabozaroid.abopay.core.network.model.c2c.transfer.C2CTransferNetworkParam
import com.jabozaroid.abopay.core.network.model.c2c.transfer.C2CTransferNetworkResult
import com.jabozaroid.abopay.core.network.model.c2c.inquiry.CardHolderNetworkInquiry

/**
 *  Created on 11/6/2024 
 **/


fun C2CTransferParam.mapToNetworkParam(): C2CTransferNetworkParam {
    return C2CTransferNetworkParam(
        pin = pin, cvv2 = cvv2,
        expireDate = expireDate,
        inquiryToken = inquiryToken

    )
}


fun C2CInquiryParam.mapToNetworkParam(): C2CInquiryNetworkParam {
    return C2CInquiryNetworkParam(
        amount = amount,
        destinationPan = destinationPan,
        token = token,
        description = description,
        sourcePan = sourcePan
    )
}

fun C2CInquiryNetworkResult.mapToDomainResult(): C2CInquiryResult {
    return C2CInquiryResult(
        cardHolderInquiry = cardHolderInquiry.mapToDomainResult()
    )
}

fun CardHolderNetworkInquiry.mapToDomainResult(): CardHolderInquiry {
    return CardHolderInquiry(
        inquiryToken = inquiryToken,
        fullName = fullName,
        isEnvelopRequired = isEnvelopRequired

    )
}

fun C2CTransferNetworkResult.mapToDomainResult(): C2CTransferResult {
    return C2CTransferResult(
        amount = amount,
        rrn = rrn,
        statusText = statusText,
        transactionDate = transactionDate,
        transactionId = transactionId,
        registrationDate = registrationDate,
        stan = stan,
        additionalResponseData = additionalResponseData,
        securityFactor = securityFactor,
        status = status,
        trackingNumber = trackingNumber,
    )
}