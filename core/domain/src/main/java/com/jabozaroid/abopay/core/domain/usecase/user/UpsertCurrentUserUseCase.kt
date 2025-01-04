package com.jabozaroid.abopay.core.domain.usecase.user

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.OfflineStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.domain.model.user.User
import com.jabozaroid.abopay.core.domain.usecase.BaseUseCase
import javax.inject.Inject
import javax.inject.Named

class UpsertCurrentUserUseCase @Inject constructor(
    @Named("sharedPreferences")
    private val cache: OfflineStorage
) : BaseUseCase<User, Boolean>() {
    override suspend fun onExecute(param: User): Boolean {
        return try {
            cache.save(StorageKey.CURRENT_USER, param)
            true
        } catch (e: Exception) {
            false
        }
    }
}