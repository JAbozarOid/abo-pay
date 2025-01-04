package com.jabozaroid.abopay.core.domain.model.user

import com.jabozaroid.abopay.core.domain.model.exception.BusinessException
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserTest {

    @Test(expected = BusinessException::class)
    fun `empty phone number test`() {

        val phoneNumber = ""
        User.create(
            User.Companion.Argument(
                phoneNumber,
                "first name",
                "last name",
                "0021477191",
            )
        )

    }
    @Test(expected = BusinessException::class)
    fun `invalid length phone number test`() {

        val phoneNumber = "4568"
        User.create(
            User.Companion.Argument(
                phoneNumber,
                "first name",
                "last name",
                "0021477191",
            )
        )

    }
    @Test(expected = BusinessException::class)
    fun `number only test`() {

        val phoneNumber = "sdswd48as2d"
        User.create(
            User.Companion.Argument(
                phoneNumber,
                "first name",
                "last name",
                "0021477191",
            )
        )

    }

}