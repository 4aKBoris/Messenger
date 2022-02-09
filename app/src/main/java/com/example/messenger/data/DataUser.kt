package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class DataUser(val firstName: String, val lastName: String? = null) {

    fun getName() = if (lastName == null) firstName else "$firstName $lastName"

    fun getInit() = "${firstName.first()}${lastName?.first()?: ""}"
}
