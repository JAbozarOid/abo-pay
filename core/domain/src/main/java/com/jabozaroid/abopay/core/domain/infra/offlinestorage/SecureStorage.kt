package com.jabozaroid.abopay.core.domain.infra.offlinestorage

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey


interface SecureStorage {

    suspend fun <T> save(key: StorageKey, data: T)

    suspend fun <T> get(key: StorageKey, type: Class<T>): T?

    suspend fun remove(key: StorageKey)

    suspend fun truncate()

    suspend fun contains(key: StorageKey): Boolean
}