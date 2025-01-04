package com.jabozaroid.abopay.core.data.dataSource.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.model.bill.DeleteBillParam

interface BillRemoteDataSource {

    suspend fun getBillTypes(): AboPayResult<BillsTypeResult?>

    suspend fun addBill(param: AddBillParam): AboPayResult<Unit>

    suspend fun editeBill(param: AddBillParam): AboPayResult<Unit>

    suspend fun deleteBill(param: DeleteBillParam): AboPayResult<Unit>

    suspend fun getBillInquiry(param: BillInquiryParam): AboPayResult<BillInquiryResult?>

    suspend fun getBills(): AboPayResult<BillsResult?>

}