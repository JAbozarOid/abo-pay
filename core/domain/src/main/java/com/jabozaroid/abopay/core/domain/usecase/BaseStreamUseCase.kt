package com.jabozaroid.abopay.core.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseStreamUseCase<PARAM, RESULT> {

    protected abstract fun onStream(param: PARAM): Flow<RESULT>
    fun start(param: PARAM): Flow<RESULT> = onStream(param)

}