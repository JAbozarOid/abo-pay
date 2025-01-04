package com.jabozaroid.abopay.core.data.repository.payment

import com.jabozaroid.abopay.core.data.dataSource.payment.PaymentDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import com.jabozaroid.abopay.core.domain.repository.payment.PaymentRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

/**
 *  Created on 12/18/2024 
 **/
class PaymentRepositoryImp @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    @Named(com.jabozaroid.abopay.core.common.PAYMENT_REMOTE_DATASOURCE) private val paymentRemoteDatasource: PaymentDataSource,
) : PaymentRepository {
    override suspend fun pay(paymentRequestParam: PaymentRequestParam): AboPayResult<PaymentResult?> {
        return withContext(dispatcherProvider.io) {
            paymentRemoteDatasource.pay(paymentRequestParam)
        }

    }
}