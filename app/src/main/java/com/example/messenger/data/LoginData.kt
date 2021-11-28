package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(val phoneNumber: String, val password: ByteArray): java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginData

        if (phoneNumber != other.phoneNumber) return false
        if (!password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = phoneNumber.hashCode()
        result = 31 * result + password.contentHashCode()
        return result
    }

}