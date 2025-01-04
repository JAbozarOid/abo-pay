package com.jabozaroid.abopay.core.domain.model.user

import com.jabozaroid.abopay.core.domain.model.exception.BusinessException

open class User private constructor(
    val phoneNumber: PhoneNumber,
    val identity: Identity? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val nationalCode: String? = null
) {
    fun logOut(): User {
        return create(
            Argument(
                phoneNumber = this.phoneNumber.value,
                nationalCode = this.nationalCode,
                firstName = this.firstName,
                lastName = this.lastName,
                identity = null
            )
        )
    }

    companion object {

        fun create(argument: Argument) =
            User(
                phoneNumber = PhoneNumber(argument.phoneNumber),
                firstName = argument.firstName,
                lastName = argument.lastName,
                nationalCode = argument.nationalCode,
                identity = if (argument.identity != null) Identity.create(argument.identity) else null
            )

        data class Argument(
            val phoneNumber: String,
            val firstName: String? = null,
            val lastName: String? = null,
            val nationalCode: String? = null,
            val identity: Identity.Companion.Argument? = null
        )

    }

}

@JvmInline
value class PhoneNumber(val value: String) {

    init {
        emptyGuard()
        lengthGuard()
        numberOnlyGuard()
    }

    private fun numberOnlyGuard() {
        val pattern = "[0-9]{11}".toRegex()
        if (!value.matches(pattern)) throw BusinessException("phone number should contains number only")
    }

    private fun lengthGuard() {
        if (value.length != 11) throw BusinessException("phone number length should be 11")
    }

    private fun emptyGuard() {
        if (value.isEmpty()) throw BusinessException("empty phone number")
    }

}


