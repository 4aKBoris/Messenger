package com.example.messenger.ui.screens.main.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.ChatUser
import com.example.messenger.data.Message
import com.example.messenger.exception.MessengerException
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

class ChatViewModel : ViewModel() {

    private val _message = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _users = mutableStateOf(listOf<ChatUser>())
    private val _messages = mutableStateOf(listOf<Message>())
    private val _scroll = mutableStateOf(false)

    val message: State<String> = _message
    val dialogState: State<Boolean> = _dialogState
    val error: State<String> = _error
    val users: State<List<ChatUser>> = _users
    val messages: State<List<Message>> = _messages
    val scroll: State<Boolean> = _scroll

    fun onChangeMessage(message: String) {
        _message.value = message
    }

    suspend fun getUsers() {
        _users.value = Requests.getUsers()
    }

    suspend fun getMessages() {
        _messages.value = Requests.getMessages()
        _scroll.value = !_scroll.value
    }

    suspend fun getMessage(id: Int) {
        while (true) {
            val message = Requests.getMessage().filterNot { it in _messages.value }
                .filterNot { it.userId == id }
            _messages.value = _messages.value + message
            _messages.value = _messages.value.sortedBy { it.dateTime }
            delay(5000L)
        }
    }

    fun getUserName(id: Int) = _users.value.first { it.id == id }.getName()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSendClick(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (_message.value.isBlank()) throw MessengerException("Введите сообщение")
            if (_message.value.length > 255) throw MessengerException("Ваше сообщение слишком длинное! Ограничение 255 символов")
            val mess = Message(
                message = _message.value,
                userId = id,
                dateTime = LocalDateTime.now().toKotlinLocalDateTime()
            )
            val flag = Requests.setMessage(message = mess)
            if (!flag) throw MessengerException("Сообщение не удалось отправить, попробуйте ещё раз!")
            else {
                _messages.value = _messages.value + mess
                _scroll.value = !_scroll.value
                _message.value = ""
            }
        } catch (e: MessengerException) {
            _error.value = e.message ?: "Ошибка"
            _dialogState.value = true
        }
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }
}