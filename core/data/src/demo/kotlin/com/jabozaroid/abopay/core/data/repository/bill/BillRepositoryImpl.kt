package com.jabozaroid.abopay.core.data.repository.bill

import com.jabozaroid.abopay.core.data.dataSource.bill.BillRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.DeleteBillParam
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val dispatcherProvider: com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider,
    private val billRemoteDataSource: BillRemoteDataSource,
) : BillRepository {

    override suspend fun getBillType(): AboPayResult<BillsTypeResult?> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.getBillTypes()
        }
    }

    override suspend fun addBill(param: AddBillParam): AboPayResult<Unit> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.addBill(param)
        }
    }

    override suspend fun editeBill(param: AddBillParam): AboPayResult<Unit> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.editeBill(param)
        }
    }

    override suspend fun deleteBill(param: DeleteBillParam): AboPayResult<Unit> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.deleteBill(param)
        }
    }

    override suspend fun getBillInquiry(param: BillInquiryParam): AboPayResult<BillInquiryResult?> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.getBillInquiry(param)
        }
    }

    override suspend fun getBills(): AboPayResult<BillsResult?> {
        return withContext(dispatcherProvider.io) {
            billRemoteDataSource.getBills()
        }
    }
}