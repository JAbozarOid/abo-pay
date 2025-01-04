package com.jabozaroid.abopay.core.domain.repository.payment

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult

/**
 *  Created on 12/18/2024
 **/
interface PaymentRepository {

    suspend fun pay(paymentRequestParam: PaymentRequestParam) : AboPayResult<PaymentResult?>
}