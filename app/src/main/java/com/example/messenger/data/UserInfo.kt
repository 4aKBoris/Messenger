package com.example.messenger.data

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val dataUser: DataUser,
    @Serializable(with = LocalDateTimeComponentSerializer::class) val registrationData: LocalDateTime
)
