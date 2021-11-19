package com.example.messenger.ui.screens.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.serialization.Serializable

class ChatViewModel: ViewModel() {

    var message = mutableStateOf("")

    fun changeMessage(text: String) {
        message.value = text
    }

}