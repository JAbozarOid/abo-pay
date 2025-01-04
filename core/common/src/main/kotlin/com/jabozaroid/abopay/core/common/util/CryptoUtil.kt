package com.jabozaroid.abopay.core.common.util

import com.jabozaroid.abopay.core.common.util.FormatUtil.Companion.base64ToByteArray
import com.jabozaroid.abopay.core.common.util.FormatUtil.Companion.byteArrayToAscii
import com.jabozaroid.abopay.core.common.util.FormatUtil.Companion.byteArrayToHexString
import com.jabozaroid.abopay.core.common.util.FormatUtil.Companion.hexToByteArray
import org.spongycastle.crypto.BufferedBlockCipher
import org.spongycastle.crypto.CipherParameters
import org.spongycastle.crypto.digests.SHA256Digest
import org.spongycastle.crypto.engines.AESEngine
import org.spongycastle.crypto.macs.HMac
import org.spongycastle.crypto.modes.CBCBlockCipher
import org.spongycastle.crypto.paddings.BlockCipherPadding
import org.spongycastle.crypto.paddings.PKCS7Padding
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.spongycastle.crypto.params.KeyParameter
import org.spongycastle.crypto.params.ParametersWithIV
import org.spongycastle.jce.provider.BouncyCastleProvider
import org.spongycastle.util.encoders.Base64
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.Security
import java.security.interfaces.RSAPublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.crypto.spec.SecretKeySpec
import kotlin.math.abs


/**
 *  Created on 11/30/2024
 **/
object CryptoUtil {
    const val DESEDE_ALGORITHM: String = "DESede/ECB/PKCS5Padding"
    const val AES_KEY_256_SIZA: Int = 256
    const val AES_KEY_128_SIZA: Int = 128

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    /**
     * Generate AES key
     *
     * @return AES key
     * @throws NoSuchAlgorithmException
     */
    @Throws(NoSuchAlgorithmException::class)
    fun generateAesKey(keySize: Int): String {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(keySize)
        return Base64.toBase64String(keyGen.generateKey().encoded)
    }

    /**
     * Encrypt data through AES encryption
     *
     * @param key  AES key in HEX
     * @param data plain data
     * @return returns encrypted data in Base64 format
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptAes(key: String, data: String): String {
        val cipher = prepareAesCipher(key, true)
        val bData = data.toByteArray()
        val buffer = ByteArray(cipher.getOutputSize(bData.size))
        var len = cipher.processBytes(bData, 0, bData.size, buffer, 0)
        len += cipher.doFinal(buffer, len)
        return FormatUtil.Companion.byteArrayToHexString(Arrays.copyOfRange(buffer, 0, len))
    }

    /**
     * Decrypt data through AES encryption
     *
     * @param key  AES key in HEX
     * @param data encrypted data in base64
     * @return returns plain data
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decryptAes(key: String, data: String?): String? {
        if (data == null) return null
        val cipher = prepareAesCipher(key, false)
        val bData = hexToByteArray(data)
        val buffer = ByteArray(cipher.getOutputSize(bData.size))
        var len = cipher.processBytes(bData, 0, bData.size, buffer, 0)
        len += cipher.doFinal(buffer, len)
        val out = Arrays.copyOfRange(buffer, 0, len)
        return String(out).trim { it <= ' ' }
    }

    /**
     * Prepare Cipher for AES encryption
     *
     * @param key        key in hex
     * @param encryption set true for encryption and false for decryption
     * @return cipher
     */
    private fun prepareAesCipher(key: String, encryption: Boolean): BufferedBlockCipher {
        val bKey = hexToByteArray(key)

        val params: CipherParameters = ParametersWithIV(KeyParameter(bKey), ByteArray(16))
        val padding: BlockCipherPadding = PKCS7Padding()
        val cipher: BufferedBlockCipher = PaddedBufferedBlockCipher(
            CBCBlockCipher(AESEngine()), padding
        )
        cipher.reset()
        cipher.init(encryption, params)
        return cipher
    }


    private fun prepareCipherWithIv(
        key: String,
        iv: ByteArray,
        forEncryption: Boolean
    ): BufferedBlockCipher {
        val bKey = hexToByteArray(key)
        val params: CipherParameters = ParametersWithIV(KeyParameter(bKey), iv)
        val padding: BlockCipherPadding = PKCS7Padding()
        val cipher: BufferedBlockCipher = PaddedBufferedBlockCipher(
            CBCBlockCipher(AESEngine()), padding
        )
        cipher.reset()
        cipher.init(forEncryption, params)
        return cipher
    }


    /**
     * Generate HMAC
     *
     * @param key  key in hex string
     * @param data data to generate mac
     * @return return hmac in base64
     */
    fun generateHMac(key: String?, data: String): String {
        val bKey = hexToByteArray(
            key!!
        )
        val bData = data.toByteArray()
        val hMac = HMac(SHA256Digest())
        hMac.init(KeyParameter(bKey))
        hMac.update(bData, 0, bData.size)
        val result = ByteArray(hMac.macSize)
        hMac.doFinal(result, 0)
        return byteArrayToHexString(result)
    }

    /**
     * Encrypt data by RSA key
     *
     * @param key  rsa key in base64
     * @param data data
     * @return encrypted data
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptRsa(key: String, data: String): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, createRSAPublicKey(key))
        return Base64.toBase64String(cipher.doFinal(data.toByteArray()))
    }

    /**
     * Encrypt data by RSA key
     *
     * @param key  RsaPublicKey
     * @param data data
     * @return encrypted data
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptRsa(key: PublicKey?, data: String): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.toBase64String(cipher.doFinal(data.toByteArray()))
    }

    /**
     * @param privateKey
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decryptRsa(privateKey: String?, publicKey: String?, data: String?): String {
        val bData = base64ToByteArray(
            data!!
        )
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, createRSAPrivateKey(privateKey, publicKey))
        return byteArrayToAscii(cipher.doFinal(bData))
    }

    @Throws(Exception::class)
    fun decryptRsa(privateKey: PrivateKey?, data: String?): String {
        val bData = base64ToByteArray(
            data!!
        )
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return byteArrayToAscii(cipher.doFinal(bData))
    }

    /**
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun createRSAPrivateKey(privateKey: String?, publicKey: String?): PrivateKey {
        val privateExponent = BigInteger(privateKey, 16)
        val publicModulus = BigInteger(publicKey, 16)

        //Build the private key
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateSpec = RSAPrivateKeySpec(publicModulus, privateExponent)
        return keyFactory.generatePrivate(privateSpec)
    }

    /**
     * @param key
     * @return
     */
    fun createRSAPublicKey(key: String): RSAPublicKey {
        val modulus = BigInteger(key, 16)
        val exponent = BigInteger("010001", 16)
        val p: RSAPublicKey = object : RSAPublicKey {
            override fun getPublicExponent(): BigInteger {
                return exponent
            }

            override fun getAlgorithm(): String {
                return "RSA/ECB/PKCS1Padding"
            }

            override fun getFormat(): String {
                return "X509"
            }

            override fun getEncoded(): ByteArray {
                return key.toByteArray()
            }

            override fun getModulus(): BigInteger {
                return modulus
            }
        }
        return p
    }

    /**
     * @param publicKey in Base64 Format
     * @return
     */
    fun getPublicKey(publicKey: String?): PublicKey? {
        try {
            val spec = X509EncodedKeySpec(Base64.decode(publicKey))
            var keyFactory: KeyFactory? = null
            keyFactory = KeyFactory.getInstance("RSA")
            return keyFactory.generatePublic(spec)
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Generate RSA KeyPair
     *
     * @return RSA KeyPair
     * @throws Exception
     */
    @Throws(Exception::class)
    fun generateRsaKey(): KeyPair {
        val kpg = KeyPairGenerator.getInstance("RSA")
        kpg.initialize(2048)
        return kpg.genKeyPair()
    }

    fun getRsaKeys(keyPair: KeyPair): List<String> {
        val keys: MutableList<String> = ArrayList()

        val hPrvKey = byteArrayToHexString(keyPair.private.encoded)
        val len = 512
        val offset = 76

        keys.add(hPrvKey.substring(offset + len + 18, offset + 18 + 2 * len))
        keys.add(hPrvKey.substring(offset, offset + len))

        return keys
    }

    /**
     * Encode String to base64 string
     *
     * @param str input string
     * @return base64 encoded string
     */
    fun base64Encode(str: String?): String? {
        if (str == null) return null
        return base64Encode(str.toByteArray())
    }

    /**
     * Encode byte array to base64 string
     *
     * @param data input byte array
     * @return base64 encoded string
     */
    fun base64Encode(data: ByteArray?): String {
        return Base64.toBase64String(data)
    }

    /**
     * Decode base64 string
     *
     * @param str base64 encoded string
     * @return plain data in byte array
     */
    fun base64Decode(str: String?): ByteArray? {
        if (str == null) return null
        return Base64.decode(str)
    }

    /**
     * Decode base64 byte array
     *
     * @param data base64 encoded byte array
     * @return plain data in byte array
     */
    fun base64Decode(data: ByteArray?): ByteArray {
        return Base64.decode(data)
    }


    /**
     * Triple DES encryption
     *
     * @param data plain data
     * @param key  key
     * @return 3DES encrypted data
     */
    fun encrypt3DES(data: String, key: String?): String? {
        var encryptedString: String? = null
        try {
            val secKey = SecretKeySpec(base64Decode(key), DESEDE_ALGORITHM)
            val cipher = Cipher.getInstance(DESEDE_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, secKey)
            val plainText = data.toByteArray()
            val encryptedText = cipher.doFinal(plainText)
            encryptedString = base64Encode(encryptedText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encryptedString
    }

    fun decrypt3DES(data: String?, key: String?): String? {
        var decryptedText: String? = null
        try {
            val cipher = Cipher.getInstance(DESEDE_ALGORITHM)
            val secKey = SecretKeySpec(base64Decode(key), DESEDE_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, secKey)
            val encryptedText = base64Decode(data)
            val plainText = cipher.doFinal(encryptedText)
            decryptedText = String(plainText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return decryptedText
    }

    /**
     * Generate SHA-256 Hash from data
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getSHA256Hash(data: String): String {
        if (data.isEmpty()) return ""
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(data.toByteArray(StandardCharsets.UTF_8))
        return base64Encode(bytes)
    }

    //region -- shaparak encryption
    @Throws(Exception::class)
    fun encryptAesWithIV(key: String, data: String, iv: ByteArray): ByteArray {
        val cipher = prepareCipherWithIv(key, iv, true)
        val bData = data.toByteArray()
        val buffer = ByteArray(cipher.getOutputSize(bData.size))
        var len = cipher.processBytes(bData, 0, bData.size, buffer, 0)
        len += cipher.doFinal(buffer, len)

        return Arrays.copyOfRange(buffer, 0, len)
    }

    @Throws(NoSuchAlgorithmException::class)
    fun generateByteArrayAesKey(keySize: Int): ByteArray {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(keySize)
        return keyGen.generateKey().encoded
    }

    fun generatePublicKey(key: ByteArray?): RSAPublicKey? {
        try {
            val keyFactory = KeyFactory.getInstance("RSA")
            val keySpec = X509EncodedKeySpec(key)
            return keyFactory.generatePublic(keySpec) as RSAPublicKey
        } catch (e: Throwable) {
            return null
        }
    }

    fun encryptOAEP(data: ByteArray?, key: ByteArray?): ByteArray? {
        try {
            val publicKey = generatePublicKey(key)
            if (publicKey != null) {
                val cipher = Cipher.getInstance("RSA/ECB/OAEPPadding")
                val oaepParameterSpec = OAEPParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec("SHA-1"),
                    PSource.PSpecified.DEFAULT
                )
                cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParameterSpec)
                return cipher.doFinal(data)
            } else return null
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }

    fun generateIV(ivSize: Int): ByteArray? {
        try {
            var ivBytes = ByteArray(ivSize)
            val r = SecureRandom()
            r.nextBytes(ivBytes)
            ivBytes = r.generateSeed(ivSize)


            for (i in ivBytes.indices) {
                if (ivBytes[i] < 0) ivBytes[i] = abs(ivBytes[i].toDouble()).toInt().toByte()
            }


            return ivBytes
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(Exception::class)
    fun decryptAesWithIV(key: String, data: String?, iv: ByteArray): String {
        //  if (data == null)
        //    return null;
        val cipher = prepareCipherWithIv(key, iv, false)
        val bData = hexToByteArray(
            data!!
        )
        val buffer = ByteArray(cipher.getOutputSize(bData.size))
        var len = cipher.processBytes(bData, 0, bData.size, buffer, 0)
        len += cipher.doFinal(buffer, len)
        val out = Arrays.copyOfRange(buffer, 0, len)
        return String(out).trim { it <= ' ' }
    } //    public static String decryptShapark(String data, String key) {
    //        String decryptedText = null;
    //        try {
    //            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    //            SecretKeySpec secKey = new SecretKeySpec(base64Decode(key), "AES/CBC/PKCS5Padding");
    //            cipher.init(Cipher.DECRYPT_MODE, secKey);
    //            byte[] encryptedText = base64Decode(data);
    //            byte[] plainText = cipher.doFinal(encryptedText);
    //            decryptedText = new String(plainText);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return decryptedText;
    //    }
    //
    //    private static BufferedBlockCipher prepareCipherWithIv(String key, byte[] iv) {
    //        byte[] bKey = FormatUtil.hexToByteArray(key);
    //        CipherParameters params = new ParametersWithIV(new KeyParameter(bKey), iv);
    //        BlockCipherPadding padding = new PKCS7Padding();
    //        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
    //                new CBCBlockCipher(new AESEngine()), padding);
    //        cipher.reset();
    //        cipher.init(true, params);
    //        return cipher;
    //    }
    //endregion
}
