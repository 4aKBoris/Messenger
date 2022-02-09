package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class Icon(val icon: ByteArray?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Icon

        if (icon != null) {
            if (other.icon == null) return false
            if (!icon.contentEquals(other.icon)) return false
        } else if (other.icon != null) return false

        return true
    }

    override fun hashCode(): Int {
        return icon?.contentHashCode() ?: 0
    }
}
