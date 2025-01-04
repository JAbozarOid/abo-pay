package com.jabozaroid.abopay.core.network.helper

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.ECGenParameterSpec
import javax.crypto.KeyAgreement
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 *  Created on 11/26/2024
 **/
class ECDH {

    private fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("EC") // الگوریتم منحنی بیضوی
        val ecSpec = ECGenParameterSpec("secp256r1") // منحنی استاندارد
        keyPairGenerator.initialize(ecSpec)
        return keyPairGenerator.generateKeyPair()
    }

    // this for share with server
    fun getClientPublicKey(): PublicKey {
        return generateKeyPair().public
    }

    fun getClientPrivateKey(): PrivateKey {
        return generateKeyPair().private
    }

    fun generateSharedSecret(ownPrivateKey: PrivateKey, receivedPublicKey: PublicKey): ByteArray {
        val keyAgreement = KeyAgreement.getInstance("ECDH")
        keyAgreement.init(ownPrivateKey)
        keyAgreement.doPhase(receivedPublicKey, true)
        return keyAgreement.generateSecret() // کلید مشترک
    }

    fun deriveKeyECDH(sharedSecret : ByteArray): ByteArray {
        val salt = "randomSalt".toByteArray()
        val info = "adfsdf".toByteArray()
        return hkdf(sharedSecret,salt,info,32)
    }


    fun hkdf(
        inputKeyMaterial: ByteArray,
        salt: ByteArray,
        info: ByteArray,
        outputLength: Int,
    ): ByteArray {
        val mac = Mac.getInstance("HmacSHA256")

        //extract
        mac.init(SecretKeySpec(salt, "HmacSHA256"))
        val prk = mac.doFinal(inputKeyMaterial)

        //expand
        mac.init(SecretKeySpec(prk, "HmacSHA256"))
        val okm = ByteArray(outputLength)
        var t = ByteArray(0)
        var written = 0
        var counter = 1.toByte()

        while (written < outputLength) {
            mac.update(t)
            mac.update(info)
            mac.update(counter)
            t = mac.doFinal()
            val toCopy = minOf(t.size, outputLength - written)
            System.arraycopy(t, 0, okm, written, toCopy)
            written += toCopy
            counter++
        }
        return okm

    }

}