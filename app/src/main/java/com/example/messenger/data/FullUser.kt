package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class FullUser(
    val id: Int,
    val user: User
)
