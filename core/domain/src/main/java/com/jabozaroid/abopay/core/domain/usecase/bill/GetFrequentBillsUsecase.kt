package com.jabozaroid.abopay.core.domain.usecase.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsResult
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetFrequentBillsUsecase @Inject constructor(private val billRepository: BillRepository) :
    BaseUseCase<Unit, AboPayResult<BillsResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<BillsResult?> {
        return billRepository.getBills()
    }
}