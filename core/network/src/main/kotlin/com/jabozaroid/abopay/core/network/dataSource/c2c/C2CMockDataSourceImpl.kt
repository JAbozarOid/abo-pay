package com.jabozaroid.abopay.core.network.dataSource.c2c

import com.jabozaroid.abopay.core.data.dataSource.c2c.C2CDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.CardHolderInquiry
import javax.inject.Inject

/**
 *  Created on 11/12/2024
 **/
class C2CMockDataSourceImpl @Inject constructor() : C2CDataSource {
    override suspend fun inquiryWithToken(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> {
        return AboPayResult.AboPaySuccess(
            C2CInquiryResult(cardHolderInquiry = CardHolderInquiry(
                fullName = "رضا دانایی فرد",
                inquiryToken = "A=544asdfASdfasvasdASDASD474487481=8adv4",
                isEnvelopRequired = false
            )
            )
        )
    }

    override suspend fun inquiryWithPan(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> {
        return AboPayResult.AboPaySuccess(
            C2CInquiryResult(
                cardHolderInquiry = CardHolderInquiry(
                    fullName = "رضا دانایی فرد",
                    inquiryToken = "A=544asdfASdfasvasdASDASD474487481=8adv4",
                    isEnvelopRequired = false
                )
            )
        )
    }

    override suspend fun c2cTransfer(param: C2CTransferParam): AboPayResult<C2CTransferResult?> {
        return AboPayResult.AboPaySuccess(
            C2CTransferResult(
         trackingNumber = "1256365",
         transactionId = "48554554",
         registrationDate = 14031216,
         transactionDate = "01/01/1970 07:23:51",
         stan = "656565",
         rrn = "123456798",
         amount ="55200",
         securityFactor = "securityfactor",
         statusText = "success",
         additionalResponseData = "adidddd"

     )

     )
    }
}