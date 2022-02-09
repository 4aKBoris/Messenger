package com.example.messenger.data

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Int,
    val message: String,
    @Serializable(with = LocalDateTimeComponentSerializer::class) val dateTime: LocalDateTime,
    val userId: Int
)