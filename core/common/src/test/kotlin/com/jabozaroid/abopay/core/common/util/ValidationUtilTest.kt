package com.jabozaroid.abopay.core.common.util

import org.junit.Test
import kotlin.test.assertEquals


class ValidationUtilTest {

    @Test
    fun `empty mobile`() {

        val phoneNumber = ""

        assertEquals(
            ValidationState.EMPTY,
            ValidationUtil.mobile(phoneNumber)
        )
    }

    @Test
    fun `invalid length mobile`() {

        val phoneNumber = "09876"

        assertEquals(
            ValidationState.INVALID_LENGTH,
            ValidationUtil.mobile(phoneNumber)
        )
    }


    @Test
    fun `invalid length phone`() {

        val phoneNumber = "02144522"

        assertEquals(
            ValidationState.INVALID_LENGTH,
            ValidationUtil.phone(phoneNumber, true)
        )

    }


    @Test
    fun `invalid  nationalCode`() {

        val nationalCode = "2220222991"

        assertEquals(
            ValidationState.INVALID,
            ValidationUtil.nationalCode(nationalCode)
        )

    }

    @Test
    fun `invalid length nationalCode`() {

        val nationalCode = "222007299"

        assertEquals(
            ValidationState.INVALID_LENGTH,
            ValidationUtil.nationalCode(nationalCode)
        )
    }

    @Test
    fun `valid email`() {

        val email = "asdfadsf@gmail.com"

        assertEquals(
            ValidationState.VALID,
            ValidationUtil.email(email)
        )

    }

    @Test
    fun `invalid email`() {

        val email = "aasdgmail.com"

        assertEquals(
            ValidationState.INVALID,
            ValidationUtil.email(email)
        )

    }
}
