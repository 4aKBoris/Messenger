package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class FullUser(val id: Int, val phoneNumber: String, val firstName: String, val lastName: String?)
