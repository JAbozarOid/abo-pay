package com.jabozaroid.abopay.core.domain.usecase.payment

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import com.jabozaroid.abopay.core.domain.repository.payment.PaymentRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 *  Created on 12/18/2024
 **/
class PaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) : BaseUseCase<PaymentRequestParam, AboPayResult<PaymentResult?>>() {
    override suspend fun onExecute(param: PaymentRequestParam) : AboPayResult<PaymentResult?> {
        return repository.pay(param)
    }
}