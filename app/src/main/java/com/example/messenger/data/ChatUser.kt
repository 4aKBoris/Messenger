package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class ChatUser(val id: Int, val dataUser: DataUser)