package com.jabozaroid.abopay.core.domain.usecase.bill

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.bill.DeleteBillParam
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteBillUsecase @Inject constructor(private val billRepository: BillRepository) :
    BaseUseCase<DeleteBillParam, AboPayResult<Unit>>() {
    override suspend fun onExecute(param: DeleteBillParam): AboPayResult<Unit> {
        return billRepository.deleteBill(param)
    }
}