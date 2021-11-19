package com.example.messenger.messages

import java.time.LocalDate
import java.time.LocalTime

data class Message(val message: String, val id: Int, val date: LocalDate, val time: LocalTime)
