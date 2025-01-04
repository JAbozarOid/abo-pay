package com.jabozaroid.abopay.core.offlinestorage.storage.securestorage

import android.annotation.TargetApi
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.KeyGenerator

/**
 * Created on 08,August,2024
 */

object SecretKeyGenerator {

    private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "KEY_ALIAS"

    fun <T> encrypt(token: T?): T? {
        return getSecurityKey()?.encrypt(token)
    }

    fun <T> decrypt(token: T?): String? {
        return getSecurityKey()?.decrypt(token)
    }

    private fun getSecurityKey(): KeyStoreSecureStorage? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateSecretKey(keyStore)
        } else
            generateKeyPairPreM(keyStore)
    }

    private val keyStore by lazy {
        var ks: KeyStore? = null
        try {
            ks = KeyStore.getInstance(ANDROID_KEY_STORE)
            ks?.load(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ks!!
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun generateSecretKey(keyStore: KeyStore): KeyStoreSecureStorage? {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    ).setBlockModes(
                        KeyProperties.BLOCK_MODE_GCM
                    )
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setRandomizedEncryptionRequired(false)
                        .build()
                )
                return KeyStoreSecureStorage(keyGenerator.generateKey())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val entry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            return KeyStoreSecureStorage(entry.secretKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun generateKeyPairPreM(keyStore: KeyStore): KeyStoreSecureStorage? {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val end = Calendar.getInstance()
                //1 Year validity
                end.add(Calendar.YEAR, 1)
                val kpg = KeyPairGenerator.getInstance("RSA", ANDROID_KEY_STORE)
                kpg.generateKeyPair()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val entry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
            return KeyStoreSecureStorage(
                KeyPair(entry.certificate.publicKey, entry.privateKey)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun clear() {
        val keyStore = keyStore
        try {
            if (keyStore.containsAlias(KEY_ALIAS)) {
                keyStore.deleteEntry(KEY_ALIAS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}