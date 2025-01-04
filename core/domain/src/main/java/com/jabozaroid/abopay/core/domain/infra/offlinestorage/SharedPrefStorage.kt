package com.jabozaroid.abopay.core.domain.infra.offlinestorage

import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey


interface SharedPrefStorage {

    fun saveString(key: StorageKey, data: String)

    fun saveInt(key: StorageKey, data: Int)

    fun saveBoolean(key: StorageKey, data: Boolean)

    fun saveLong(key: StorageKey, data: Long)

    fun saveFloat(key: StorageKey, data: Float)

    fun <T> save(key: StorageKey, data: T)

    fun getString(key: StorageKey): String

    fun getInt(key: StorageKey): Int

    fun getBoolean(key: StorageKey): Boolean

    fun getLong(key: StorageKey): Long

    fun getFloat(key: StorageKey): Float

    fun <T> get(key: StorageKey, type: Class<T>): T?

    fun remove(key: StorageKey)

    fun truncate()

    fun contains(key: StorageKey): Boolean

}