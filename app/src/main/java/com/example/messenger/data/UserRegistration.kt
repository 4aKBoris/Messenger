package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistration(val user: User, val icon: String?)
