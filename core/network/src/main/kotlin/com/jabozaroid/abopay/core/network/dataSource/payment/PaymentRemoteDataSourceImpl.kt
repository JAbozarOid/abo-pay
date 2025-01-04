package com.jabozaroid.abopay.core.network.dataSource.payment

import com.jabozaroid.abopay.core.data.dataSource.payment.PaymentDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.payment.PaymentRequestParam
import com.jabozaroid.abopay.core.domain.model.payment.PaymentResult
import com.jabozaroid.abopay.core.network.api.payment.PaymentApi
import com.jabozaroid.abopay.core.network.dataSource.payment.mapper.mapToDomainModel
import com.jabozaroid.abopay.core.network.dataSource.payment.mapper.mapToNetworkModel
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  Created on 12/18/2024 
 **/
class PaymentRemoteDataSourceImpl @Inject constructor(
    private val paymentApi: PaymentApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
) :
    PaymentDataSource {
    override suspend fun pay(paymentRequestParam: PaymentRequestParam): AboPayResult<PaymentResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                paymentApi.pay(paymentRequestParam.mapToNetworkModel())
            }
        }.map {
            it.data?.mapToDomainModel()
        }



}