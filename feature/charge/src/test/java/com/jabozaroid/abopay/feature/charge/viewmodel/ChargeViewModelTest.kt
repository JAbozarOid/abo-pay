package com.jabozaroid.abopay.feature.charge.viewmodel

import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.designsystem.component.model.MobileNumberUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayResult
import com.jabozaroid.abopay.core.domain.model.charge.param.favourite.DeleteFavouriteChargeNumberParam
import com.jabozaroid.abopay.core.domain.model.charge.result.topup.TopUpChargeResult
import com.jabozaroid.abopay.core.domain.usecase.charge.DeleteFavoriteMobileNumberUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetFavouriteChargeNumUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetPinChargesUseCase
import com.jabozaroid.abopay.core.domain.usecase.charge.GetTopUpChargesUseCase
import com.jabozaroid.abopay.core.test.rule.MainDispatcherRule
import com.jabozaroid.abopay.feature.charge.model.ChargeAction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue


@RunWith(JUnit4::class)
class ChargeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    //region mock UseCases
    private val getTopUpChargesUseCase: GetTopUpChargesUseCase =
        mockk(relaxed = true) // relax which create a base implement for UseCase
    private val getPinChargesUseCase: GetPinChargesUseCase = mockk()
    private val getFavouriteChargeNumUseCase: GetFavouriteChargeNumUseCase = mockk(relaxed = true)
    private val deleteFavoriteMobileNumberUseCase: DeleteFavoriteMobileNumberUseCase =
        mockk(relaxed = true)
    //endregion

    // viewModel with the mocked UseCase
    private lateinit var viewModel: ChargeViewModel

    @Before
    fun setUp() {
        // inject the mock into the viewModel
        // viewModel must be real or spy
        viewModel = spyk(
            ChargeViewModel(
                getTopUpChargesUseCase,
                getPinChargesUseCase,
                getFavouriteChargeNumUseCase,
                deleteFavoriteMobileNumberUseCase
            )
        )
    }

    @Test
    fun `when response of GetTopUpCharges api is Success the loading state should be false`() {
        runTest {
            // coEvery and every must be first for arrangement and must be return
            coEvery {
                getTopUpChargesUseCase.execute(null)
            } returns AboPayResult.AboPaySuccess<TopUpChargeResult>(
                TopUpChargeResult(
                    isActiveTransportation = false,
                    operators = listOf()
                )
            )
            viewModel.process(ChargeAction.GetTopUpCharges)

            advanceUntilIdle()

            // 100% is run
            coVerify(exactly = 1) {
                getTopUpChargesUseCase.execute(null)
            }

            // expect the state of loading is false
            assertFalse(
                viewModel.currentState.loading,
                message = "the result is ${viewModel.currentState.loading}"
            )
        }

    }

    @Test
    fun `when response of GetTopUpCharge api is Fail has error should be true`() {
        runTest {
            coEvery {
                getTopUpChargesUseCase.execute(null)
            } returns AboPayResult.AboPayException(AboPayExceptionMessage("Error happened"))

            viewModel.process(ChargeAction.GetTopUpCharges)

            advanceUntilIdle()

            coVerify(exactly = 1) {
                getTopUpChargesUseCase.execute(null)
            }

            assertTrue(viewModel.currentState.hasError)
        }
    }

    @Test
    fun `if phone section is empty the icon of operators should be off it means the index of list is null`() {

        // given
        val mobileNumberUiModel = MobileNumberUiModel(value = "")

        if (mobileNumberUiModel.value.isEmpty()) {
            // when
            viewModel.process(ChargeAction.ClearOperatorStatusByPhoneEmpty(null))
        }

        // then
        assertNull(viewModel.currentState.operatorIndex)
    }

    @Test
    fun `when phone number is valid the error message should be null`() {
        val mobileNumberUiModel = MobileNumberUiModel(value = "09125988667")

        // inside of this action the method checkPhoneNumberValidation is called
        viewModel.process(ChargeAction.UserPhoneNumberValue(phoneNumber = mobileNumberUiModel.value))

        assertNull(viewModel.currentState.mobileNumberUiModel.errorMessage)
    }

    @Test
    fun `when phone number is not valid so the error message is not null`() {
        val mobileNumberUiModel = MobileNumberUiModel(value = "9875")


        viewModel.checkPhoneNumberError(mobileNumberUiModel.value)
        // inside of the checkPhoneNumberError method the Validation util is called
        val res = ValidationUtil.mobile(mobileNumberUiModel.value)

        assertEquals(ValidationState.INVALID, res)
        assertNotNull(viewModel.currentState.mobileNumberUiModel.errorMessage)
    }

    @Test
    fun `when response of delete favourite number api is success so loading state should be false`() {

        viewModel.updateState {
            it.copy(
                loading = true
            )
        }
        println("loading state first: ${viewModel.currentState.loading}")
        assertTrue(viewModel.currentState.loading)

        runTest {
            coEvery {
                val mock = mockk<DeleteFavouriteChargeNumberParam>()
                deleteFavoriteMobileNumberUseCase.execute(
                    mock
                )
            } returns AboPayResult.AboPaySuccess<Boolean>(true)

            viewModel.process(ChargeAction.DeleteUserPhoneNumber("09125988667"))

            advanceUntilIdle()

            coVerify(exactly = 0) {
                val mock = mockk<DeleteFavouriteChargeNumberParam>()
                deleteFavoriteMobileNumberUseCase.execute(
                    mock
                )
            }

            println("loading state last: ${viewModel.currentState.loading}")
            assertFalse(viewModel.currentState.loading)
        }
    }

    @Test
    fun `when phoneNumber is not valid the border of the textField should have error`() {
        val mobileNumber = MobileNumberUiModel(value = "0912")

        viewModel.checkPhoneNumberErrorStatus(mobileNumber.value)
        val res = ValidationUtil.mobile(mobileNumber.value)
        assertEquals(ValidationState.INVALID, res)
        assertTrue(viewModel.currentState.mobileNumberUiModel.errorStatus)
    }

    @Test
    fun `search the first four digits of the mobile number in category list`() {
        //given
        val phoneNumber = "0914315155"
        val operatorList =
            listOf<IconItemUiModel>(
                IconItemUiModel(
                    index = 0,
                    categoryCodesLst = listOf("0914,0911,0912,0913,0914,0915,0916")
                ),
                IconItemUiModel(
                    index = 1,
                    categoryCodesLst = listOf("0930,0933,0935,0937,0939")
                )
            )

        //viewModel.operatorFinderByIndex(phoneNumber = phoneNumber, operatorList = operatorList)

        for (item in operatorList) {
            for (code in item.categoryCodesLst) {
                println("the code is $code")
                println("the code index is${item.index}")

                if (phoneNumber.contentEquals(code)) {
                    println("the match item is ${item.index}")
                    println("the match code is $code")
                }
            }
        }

    }
}



