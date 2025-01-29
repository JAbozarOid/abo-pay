package com.jabozaroid.abopay.feature.charge.repository

import com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider
import com.jabozaroid.abopay.core.data.dataSource.charge.ChargeRemoteDataSource
import com.jabozaroid.abopay.core.data.repository.charge.ChargeRepositoryImpl
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.test.rule.MainDispatcherRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ChargeRepositoryImplTest {

    private val chargeRemoteDataSource: ChargeRemoteDataSource = mockk()
    private val dispatcherProvider: DispatcherProvider = mockk()


    private val chargeRepository: ChargeRepository = ChargeRepositoryImpl(
        chargeRemoteDataSource = chargeRemoteDataSource,
        dispatcherProvider = dispatcherProvider

    )

    @Test
    fun verifyTopUpChargesFetchSuccessfully() = runTest {
        val result = coEvery {
            chargeRemoteDataSource.getTopUpCharges()
        } returns AboPayResult.AboPaySuccess<TopUpChargeResult>(
            TopUpChargeResult(
                isActiveTransportation = false,
                operators = listOf()
            )
        )

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


}



