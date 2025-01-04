package com.jabozaroid.abopay.core.domain.usecase.user

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.user.User
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val cache: SharedPrefStorage
) : BaseUseCase<Unit?, User?>() {
    override suspend fun onExecute(param: Unit?): User? {
        val user = cache.get(StorageKey.CURRENT_USER, User::class.java)
        return try {
            user
        } catch (e: Exception) {
            null
        }
        return null
    }

}