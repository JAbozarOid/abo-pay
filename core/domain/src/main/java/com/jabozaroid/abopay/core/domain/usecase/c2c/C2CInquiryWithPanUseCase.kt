package com.jabozaroid.abopay.core.domain.usecase.c2c

import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryParam
import com.jabozaroid.abopay.core.domain.model.c2c.inquiry.C2CInquiryResult
import com.jabozaroid.abopay.core.domain.repository.c2c.C2CRepository
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

/**
 *  Created on 11/9/2024
 **/
class C2CInquiryWithPanUseCase @Inject constructor(
    private val c2CRepository: C2CRepository,
) : BaseUseCase<C2CInquiryParam, AboPayResult<C2CInquiryResult?>>() {
    override suspend fun onExecute(param: C2CInquiryParam): AboPayResult<C2CInquiryResult?> {
        return c2CRepository.inquiryWithPan(param)
    }

}
