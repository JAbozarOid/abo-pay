package com.jabozaroid.abopay.core.domain.infra.offlinestorage

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey


interface OfflineStorage {

    suspend fun saveString(key: StorageKey, data: String)

    suspend fun saveInt(key: StorageKey, data: Int)

    suspend fun saveBoolean(key: StorageKey, data: Boolean)

    suspend fun saveLong(key: StorageKey, data: Long)

    suspend fun saveFloat(key: StorageKey, data: Float)

    suspend fun <T> save(key: StorageKey, data: T)

    suspend fun getString(key: StorageKey): String

    suspend fun getInt(key: StorageKey): Int

    suspend fun getBoolean(key: StorageKey): Boolean

    suspend fun getLong(key: StorageKey): Long

    suspend fun getFloat(key: StorageKey): Float

    suspend fun <T> get(key: StorageKey, type: Class<T>): T?

    suspend fun remove(key: StorageKey)

    suspend fun truncate()

    suspend fun contains(key: StorageKey): Boolean

}