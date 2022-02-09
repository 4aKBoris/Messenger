package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class Name(val firstName: String, val lastName: String?)
