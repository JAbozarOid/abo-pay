package com.jabozaroid.abopay.core.domain.usecase.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.BillsTypeResult
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetBillTypesUsecase @Inject constructor(
    private val billRepository: BillRepository,
) : BaseUseCase<Unit, AboPayResult<BillsTypeResult?>>() {
    override suspend fun onExecute(param: Unit): AboPayResult<BillsTypeResult?> {
        return billRepository.getBillType()
    }
}