package com.jabozaroid.abopay.core.network.dataSource.payment

import com.jabozaroid.abopay.core.data.dataSource.payment.PaymentDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import javax.inject.Inject

/**
 *  Created on 12/18/2024 
 **/
class PaymentMockDataSourceImpl @Inject constructor() : PaymentDataSource {
    override suspend fun pay(paymentRequestParam: PaymentRequestParam): AboPayResult<PaymentResult?> {
        return AboPayResult.AboPaySuccess(mockedPaymentResult)
    }
}