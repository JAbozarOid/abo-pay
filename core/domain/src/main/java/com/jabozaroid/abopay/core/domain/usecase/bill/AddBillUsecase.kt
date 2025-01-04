package com.jabozaroid.abopay.core.domain.usecase.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.AddBillParam
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddBillUsecase @Inject constructor(
    private val billRepository: BillRepository,
) : BaseUseCase<AddBillParam, AboPayResult<Unit>>() {
    override suspend fun onExecute(param: AddBillParam): AboPayResult<Unit> {
        return billRepository.addBill(param)
    }
}