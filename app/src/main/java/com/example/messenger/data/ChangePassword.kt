package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassword(val data: LoginData, val newPassword: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChangePassword

        if (data != other.data) return false
        if (!newPassword.contentEquals(other.newPassword)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + newPassword.contentHashCode()
        return result
    }

}
