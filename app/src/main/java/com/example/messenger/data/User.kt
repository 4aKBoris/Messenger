package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class User(val data: LoginData, val dataUser: DataUser)
