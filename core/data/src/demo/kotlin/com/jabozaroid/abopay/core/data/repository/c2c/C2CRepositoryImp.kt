package com.jabozaroid.abopay.core.data.repository.c2c

import com.jabozaroid.abopay.core.data.dataSource.c2c.C2CDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.domain.repository.c2c.C2CRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

/**
 *  Created on 11/9/2024 
 **/
class C2CRepositoryImp @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    @Named(com.jabozaroid.abopay.core.common.C2C_REMOTE_DATASOURCE)
    private val c2CRemoteDataSource: C2CDataSource,
) : C2CRepository {
    override suspend fun inquiryWithToken(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> =
        withContext(dispatcherProvider.io) {
            c2CRemoteDataSource.inquiryWithToken(param)
        }


    override suspend fun inquiryWithPan(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> =
        withContext(dispatcherProvider.io) {
            c2CRemoteDataSource.inquiryWithPan(param)
        }


    override suspend fun c2cTransfer(param: C2CTransferParam): AboPayResult<C2CTransferResult?> =
        withContext(dispatcherProvider.io) {
            c2CRemoteDataSource.c2cTransfer(param)
        }

}