package com.jabozaroid.abopay.core.data.dataSource.c2c

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult

/**
 *  Created on 11/6/2024
 **/
interface C2CDataSource {

    suspend fun inquiryWithToken(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?>
    suspend fun inquiryWithPan(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?>
    suspend fun c2cTransfer(param: C2CTransferParam): AboPayResult<C2CTransferResult?>

}