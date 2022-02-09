package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val firstName: String, val lastName: String?) {

    fun getName() = if (lastName == null) firstName else "$firstName $lastName"

}
