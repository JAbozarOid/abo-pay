package com.jabozaroid.abopay.core.domain.usecase.c2c

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferParam
import com.jabozaroid.abopay.core.domain.model.c2c.transfer.C2CTransferResult
import com.jabozaroid.abopay.core.domain.repository.c2c.C2CRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 *  Created on 11/9/2024 
 **/
class C2CTransferUseCase @Inject constructor(
    private val c2cRepository: C2CRepository,
) : BaseUseCase<C2CTransferParam, AboPayResult<C2CTransferResult?>>() {
    override suspend fun onExecute(param: C2CTransferParam): AboPayResult<C2CTransferResult?> {
        return c2cRepository.c2cTransfer(param)
    }
}