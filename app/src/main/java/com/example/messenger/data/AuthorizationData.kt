package com.example.messenger.data

import kotlinx.serialization.Serializable
import java.security.MessageDigest

@Serializable
data class AuthorizationData(private val phoneNumber: String, private val password: String) {

    override fun toString(): String {
        return "Digest username=$phoneNumber, realm=\"$realm\", nonce=\"undefined\", uri=\"/authorization\", algorithm=$digestAlgorithm, response=\"$digestPassword\""
    }

    private fun getSHA256Digest(str: String): ByteArray =
        MessageDigest.getInstance(digestAlgorithm).digest(str.toByteArray(Charsets.UTF_8))

    private val digestPassword: String
        get() = getSHA256Digest("$phoneNumber:$realm:$password").contentToString()

    companion object {
        private const val realm = "RestAPI"
        private const val digestAlgorithm = "MD5"
    }

}