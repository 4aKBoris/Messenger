package com.example.messenger.ui.screens.main.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ChatViewModel: ViewModel() {

    var message = mutableStateOf("")

    fun changeMessage(text: String) {
        message.value = text
    }

}