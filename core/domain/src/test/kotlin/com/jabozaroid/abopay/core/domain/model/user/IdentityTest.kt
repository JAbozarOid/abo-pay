package com.jabozaroid.abopay.core.domain.model.user

import com.jabozaroid.abopay.core.domain.model.exception.BusinessException
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdentityTest {

    @Test(expected = BusinessException::class)
    fun `empty access token test`() {

        val token = ""

        Identity.create(
            Identity.Companion.Argument(
                accessToken = token,
                refreshToken = "refresh token value"
            )
        )

    }

    @Test(expected = BusinessException::class)
    fun `empty refresh token test`() {

        val token = ""

        Identity.create(
            Identity.Companion.Argument(
                accessToken = "access token value",
                refreshToken = token
            )
        )
    }

}