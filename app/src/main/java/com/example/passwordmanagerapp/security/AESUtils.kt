package com.example.passwordmanagerapp.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AESUtils {
    // instance of keystore
    private val keystore = KeyStore.getInstance("AndroidKeyStore").apply{
        load(null)
    }

    // if key is already present in the keystore then we don't have to create new key by using instance of keystore.
    private fun getKey() : SecretKey{
        val existingKey = keystore.getEntry("secret",null) as? KeyStore.SecretKeyEntry
        // if the existing key is null we create new key
        return existingKey?.secretKey ?: createKey()
    }

    // Create a key
    private fun createKey() : SecretKey{
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(BLOCK_MODE).setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false) // for biometric
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    // Create a cypher for encryption
    private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE,getKey())
    }

    // Create a cypher for decryption (iv -> initialization vector)
    private fun getDecryptCypherForIv(iv : ByteArray) : Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,getKey(),IvParameterSpec(iv))
        }
    }


    // public function for encrypt and decrypt that can be used by other activity/fragments
    fun encrypt(text : String) : String? {
        return try{
            val cipher = encryptCipher
            val cipherText = Base64.encodeToString(cipher.doFinal(text.toByteArray()),Base64.DEFAULT)
            val iv = Base64.encodeToString(cipher.iv,Base64.DEFAULT)
            "${cipherText}.$iv"
        }catch (e : Exception){
            Log.e("TAG","encrypt : error msg = ${e.message}")
            null
        }
    }
    fun decrypt(text : String) : String? {
        val array = text.split(".")
        val cipherData = Base64.decode(array[0],Base64.DEFAULT)
        val iv = Base64.decode(array[1],Base64.DEFAULT)

        return try {
            val cipher = getDecryptCypherForIv(iv)
            val decryptedText = cipher.doFinal(cipherData)
            String(decryptedText,0,decryptedText.size,StandardCharsets.UTF_8)
        }catch (e : Exception){
            Log.e("TAG","decrypt : error msg -> @${e.message}")
            null
        }
    }

    companion object{
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}