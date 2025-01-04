package com.jabozaroid.abopay.core.offlinestorage.storage.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext
    private val context: Context
) : SharedPrefStorage {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ABO_PAY_SHP_INTERNAL", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun <T> save(key: StorageKey, data: T) {
        saveString(key, gson.toJson(data))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StorageKey, type: Class<T>): T =
        try {
            gson.fromJson(getString(key), type)
        } catch (e: Exception) {
            IllegalArgumentException("Unsupported Type $type") as T
        }

    override fun truncate() {
        sharedPreferences.edit().clear().apply()
    }

    override fun contains(key: StorageKey) =
        sharedPreferences.contains(key.name)


    override fun remove(key: StorageKey) {
        sharedPreferences.edit().remove(key.name).apply()
    }

    override fun saveString(key: StorageKey, data: String) {
        sharedPreferences.edit()
            .putString(key.toString(), data)
            .apply()
    }

    override fun saveInt(key: StorageKey, data: Int) {
        sharedPreferences.edit()
            .putInt(key.toString(), data)
            .apply()
    }

    override fun saveBoolean(key: StorageKey, data: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key.toString(), data)
            .apply()
    }

    override fun saveLong(key: StorageKey, data: Long) {
        sharedPreferences.edit()
            .putLong(key.toString(), data)
            .apply()
    }

    override fun saveFloat(key: StorageKey, data: Float) {
        sharedPreferences.edit()
            .putFloat(key.toString(), data)
            .apply()
    }

    override fun getString(key: StorageKey): String =
        sharedPreferences.getString(key.toString(), "") ?: ""

    override fun getBoolean(key: StorageKey): Boolean =
        sharedPreferences.getBoolean(key.toString(), false)

    override fun getFloat(key: StorageKey): Float =
        sharedPreferences.getFloat(key.toString(), 0F)

    override fun getLong(key: StorageKey): Long =
        sharedPreferences.getLong(key.toString(), 0L)

    override fun getInt(key: StorageKey): Int =
        sharedPreferences.getInt(key.toString(), 0)

}