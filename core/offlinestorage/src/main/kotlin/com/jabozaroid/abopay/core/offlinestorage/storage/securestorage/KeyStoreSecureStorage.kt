package com.jabozaroid.abopay.core.offlinestorage.storage.securestorage

import android.os.Build
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.OfflineStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SecureStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import java.security.GeneralSecurityException
import java.security.KeyPair
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created on 05,August,2024
 */

@Singleton
class KeyStoreSecureStorage @Inject constructor() : SecureStorage {

    companion object {
        private const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        private const val AES_MODE_FOR_POST_API_23 = "AES/GCM/NoPadding"
        private const val TAG = "keystore"
    }

    private var secretKey: SecretKey? = null
    private var keyPair: KeyPair? = null

    @Inject
    @Named("dataStore")
    lateinit var dataStoreStorage: OfflineStorage

    constructor(secretKey: SecretKey?) : this() {
        this.secretKey = secretKey
    }

    constructor(keyPair: KeyPair?) : this() {
        this.keyPair = keyPair
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> encrypt(token: T): T? {
        if (token == null) return null
        try {
            val cipher = getCipher(Cipher.ENCRYPT_MODE)
            val encrypted = cipher.doFinal(token.toString().toByteArray())
            return Base64.encodeToString(encrypted, Base64.URL_SAFE) as T
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "encrypt_error: $e")
        }
        //Unable to encrypt Token
        return null
    }

    fun <T> decrypt(encryptedToken: T?): String? {
        if (encryptedToken == null) return null
        try {
            val cipher = getCipher(Cipher.DECRYPT_MODE)
            val decoded = Base64.decode(encryptedToken.toString(), Base64.URL_SAFE)
            val original = cipher.doFinal(decoded)
            return String(original)
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "decrypt_error: $e")
        }
        //Unable to decrypt encrypted Token
        return null
    }

    @Throws(GeneralSecurityException::class)
    private fun getCipher(mode: Int): Cipher {
        val cipher: Cipher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cipher = Cipher.getInstance(AES_MODE_FOR_POST_API_23)
            cipher.init(
                mode,
                secretKey,
                GCMParameterSpec(128, AES_MODE_FOR_POST_API_23.toByteArray(), 0, 12)
            )
        } else {
            cipher = Cipher.getInstance(RSA_MODE)
            cipher.init(
                mode,
                if (mode == Cipher.DECRYPT_MODE) keyPair!!.public else keyPair!!.private
            )
        }
        return cipher
    }

    override suspend fun <T> save(key: StorageKey, data: T) {
        val encryptedValue = SecretKeyGenerator.encrypt(Gson().toJson(data))
        encryptedValue?.let {
            dataStoreStorage.saveString(key, it as String)
            Log.d(TAG, "save: $it")
        }
    }


    override suspend fun <T> get(key: StorageKey, type: Class<T>): T? {
        val storeData = dataStoreStorage.getString(key)
        return Gson().fromJson(SecretKeyGenerator.decrypt(storeData), type) as T
    }

    override suspend fun remove(key: StorageKey) {
        dataStoreStorage.remove(key)
    }

    override suspend fun truncate() {
        SecretKeyGenerator.clear()
        dataStoreStorage.truncate()
    }

    override suspend fun contains(key: StorageKey): Boolean = dataStoreStorage.contains(key)

}

