package com.jabozaroid.abopay.core.network.dataSource.c2c

import com.jabozaroid.abopay.core.data.dataSource.c2c.C2CDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.network.api.c2c.CardToCardApi
import com.jabozaroid.abopay.core.network.dataSource.c2c.mapper.mapToDomainResult
import com.jabozaroid.abopay.core.network.dataSource.c2c.mapper.mapToNetworkParam
import com.jabozaroid.abopay.core.network.helper.execute
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  Created on 11/6/2024
 **/
class C2CRemoteDataSourceImp @Inject constructor(
    private val c2cApi: CardToCardApi,
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider

) : C2CDataSource {
    override suspend fun inquiryWithToken(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                c2cApi.inquiryWithToken(param.mapToNetworkParam())            }
        }.map {
            it.data?.mapToDomainResult()
        }


    override suspend fun inquiryWithPan(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> =
        execute {
            withContext(dispatcherProvider.io) {
                c2cApi.inquiryWithPan(param.mapToNetworkParam())
            }
        }.map {
            it.data?.mapToDomainResult()
        }


    override suspend fun c2cTransfer(param: C2CTransferParam): AboPayResult<C2CTransferResult?> =
    execute {
        withContext(dispatcherProvider.io) {
            c2cApi.cardTransfer(param.mapToNetworkParam())
        }
    }.map {
        it.data?.mapToDomainResult()
    }
}

