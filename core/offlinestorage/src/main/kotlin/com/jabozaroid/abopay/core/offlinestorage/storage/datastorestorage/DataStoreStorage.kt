package com.jabozaroid.abopay.core.offlinestorage.storage.datastorestorage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.OfflineStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created on 08,August,2024
 */

const val DATA_STORE_NAME = "abo_pay_datastore"

@Singleton
class DataStoreStorage @Inject constructor() : OfflineStorage {

    @ApplicationContext
    @Inject
    lateinit var context: Context

    private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)
    override suspend fun saveString(key: StorageKey, data: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key.name)] = data
        }
    }

    override suspend fun saveInt(key: StorageKey, data: Int) {
        context.dataStore.edit {
            it[intPreferencesKey(key.name)] = data
        }
    }

    override suspend fun saveBoolean(key: StorageKey, data: Boolean) {
        context.dataStore.edit {
            it[booleanPreferencesKey(key.name)] = data
        }
    }

    override suspend fun saveLong(key: StorageKey, data: Long) {
        context.dataStore.edit {
            it[longPreferencesKey(key.name)] = data
        }
    }

    override suspend fun saveFloat(key: StorageKey, data: Float) {
        context.dataStore.edit {
            it[floatPreferencesKey(key.name)] = data
        }
    }

    override suspend fun <T> save(key: StorageKey, data: T) {
        context.dataStore.edit {
            it[stringPreferencesKey(key.name)] = Gson().toJson(data)
        }
    }

    override suspend fun getString(key: StorageKey): String =
        context.dataStore.data.first()[stringPreferencesKey(key.name)] ?: ""

    override suspend fun getInt(key: StorageKey): Int =
        context.dataStore.data.first()[intPreferencesKey(key.name)] ?: -1

    override suspend fun getBoolean(key: StorageKey): Boolean =
        context.dataStore.data.first()[booleanPreferencesKey(key.name)] ?: false

    override suspend fun getLong(key: StorageKey): Long =
        context.dataStore.data.first()[longPreferencesKey(key.name)] ?: 0L

    override suspend fun getFloat(key: StorageKey): Float =
        context.dataStore.data.first()[floatPreferencesKey(key.name)] ?: 0F

    override suspend fun <T> get(key: StorageKey, type: Class<T>): T? {
        return context.dataStore.data.map { pref ->
            val prefKey: Preferences.Key<*>? = pref.asMap().keys.find { it.name == key.name }
            return@map prefKey?.let {
                val value: T = pref[it] as T

                try {
                    Gson().fromJson(value as String, type)
                } catch (e: Exception) {
                    null
                }
            }
        }.cancellable().firstOrNull()
    }

    override suspend fun remove(key: StorageKey) {
        context.dataStore.edit { pref ->
            val prefKey = pref.asMap().keys.find { it.name == key.name } ?: return@edit
            pref.remove(prefKey)
        }
    }

    override suspend fun truncate() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }

    override suspend fun contains(key: StorageKey): Boolean {
        return context.dataStore.data.map { pref ->
            pref.asMap().filter { it.key.name == key.name }.isNotEmpty()
        }.firstOrNull() ?: false
    }

}