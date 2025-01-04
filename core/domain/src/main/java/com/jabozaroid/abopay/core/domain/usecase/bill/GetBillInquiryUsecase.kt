package com.jabozaroid.abopay.core.domain.usecase.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryResult
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetBillInquiryUsecase @Inject constructor(private val billRepository: BillRepository) :
    BaseUseCase<BillInquiryParam, AboPayResult<BillInquiryResult?>>() {
    override suspend fun onExecute(param: BillInquiryParam): AboPayResult<BillInquiryResult?> {
        return billRepository.getBillInquiry(param)
    }
}