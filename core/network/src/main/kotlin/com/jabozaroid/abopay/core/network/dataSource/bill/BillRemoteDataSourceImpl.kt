package com.jabozaroid.abopay.core.network.dataSource.bill

import com.jabozaroid.abopay.core.data.dataSource.bill.BillRemoteDataSource
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.map
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.DeleteBillParam
import com.jabozaroid.abopay.core.network.api.bill.BillApi
import com.jabozaroid.abopay.core.network.dataSource.bill.mapper.mapBillsTypeToDomainModel
import com.jabozaroid.abopay.core.network.dataSource.bill.mapper.mapToAddBillNetworkParam
import com.jabozaroid.abopay.core.network.dataSource.bill.mapper.mapToBillInquiryNetworkParam
import com.jabozaroid.abopay.core.network.dataSource.bill.mapper.mapToBillInquiryResultDomain
import com.jabozaroid.abopay.core.network.dataSource.bill.mapper.mapToBillsResultDomain
import com.jabozaroid.abopay.core.network.helper.execute
import javax.inject.Inject

class BillRemoteDataSourceImpl @Inject constructor(
    private val billApi: BillApi,
) : BillRemoteDataSource {
    override suspend fun getBillTypes(): AboPayResult<BillsTypeResult?> =
        execute {
            billApi.getBillType()
        }.map {
            it.data?.mapBillsTypeToDomainModel()
        }

    override suspend fun addBill(param: AddBillParam) =
        execute {
            billApi.addBill(param.mapToAddBillNetworkParam())
        }

    override suspend fun editeBill(param: AddBillParam): AboPayResult<Unit> =
        execute {
            billApi.updateBill(param.mapToAddBillNetworkParam())
        }

    override suspend fun deleteBill(param: DeleteBillParam): AboPayResult<Unit> =
        execute {
            billApi.deleteBill(billId = param.billId, billType = param.billType)
        }

    override suspend fun getBillInquiry(param: BillInquiryParam): AboPayResult<BillInquiryResult?> =
        execute {
          billApi.getBillInquiry(param.mapToBillInquiryNetworkParam())
        }.map {
            it.data?.mapToBillInquiryResultDomain()
        }

    override suspend fun getBills(): AboPayResult<BillsResult?> =
        execute {
            billApi.getBills()
        }.map {
            it.data?.mapToBillsResultDomain()
        }
}