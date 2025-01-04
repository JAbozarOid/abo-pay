package com.jabozaroid.abopay.core.data.dataSource.payment

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult

/**
 *  Created on 12/18/2024
 **/
interface PaymentDataSource {

    suspend fun pay(paymentRequestParam: PaymentRequestParam) : AboPayResult<PaymentResult?>
}