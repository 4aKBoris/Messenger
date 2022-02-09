package com.example.messenger.ui.screens.main.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.FullUser
import com.example.messenger.data.Message
import com.example.messenger.data.MyMessage
import com.example.messenger.data.User
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.navigation.screens.PasswordScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException

class ChatViewModel : ViewModel() {

    private val _message = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _users = mutableStateOf(listOf<User>())
    private val _messages = mutableStateOf(listOf<Message>())
    private val _scroll = mutableStateOf(false)
    private val _dialogSettings = mutableStateOf(false)
    private val _dialogAbout = mutableStateOf(false)
    private val _dialogInfo = mutableStateOf(false)

    val message: State<String> = _message
    val dialogState: State<Boolean> = _dialogState
    val error: State<String> = _error
    val users: State<List<User>> = _users
    val messages: State<List<Message>> = _messages
    val scroll: State<Boolean> = _scroll
    val dialogSettings: State<Boolean> = _dialogSettings
    val dialogAbout: State<Boolean> = _dialogAbout
    val dialogInfo: State<Boolean> = _dialogInfo

    fun onChangeDialogSettings(state: Boolean) {
        _dialogSettings.value = state
    }

    fun onChangeDialogAbout(state: Boolean) {
        _dialogAbout.value = state
    }

    fun onChangeDialogInfo(state: Boolean) {
        _dialogInfo.value = state
    }

    suspend fun getUserInfo(): FullUser? {
        return try {
            Requests.getUserInfo()
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            null
        }
    }

    fun onDelete(navController: NavController) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val flag = Requests.deleteUser()
                if (!flag) throw MessengerException("Не удалось удалить профиль! Попробуйте ещё раз")
                onChangeDialogSettings(false)
                withContext(Dispatchers.Main) {
                    navController.backQueue.clear()
                    navController.navigate(MainScreens.Welcome.createRoute())
                }
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            }
        }

    fun onPasswordChangeNavigation(
        navController: NavController,
    ) {
        navController.navigate(PasswordScreens.OldPassword.createRoute())
        onChangeDialogSettings(state = false)
    }

    fun onDataChangeNavigation(
        navController: NavController,
    ) {
        navController.navigate(MainScreens.SettingsData.createRoute())
        onChangeDialogSettings(state = false)
    }

    fun onChangeMessage(message: String) {
        _message.value = message
    }

    suspend fun getUsers() {
        try {
            _users.value = Requests.getUsers()
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
        }
    }

    suspend fun getMessages() {
        try {
            _messages.value = Requests.getMessages()
            _scroll.value = !_scroll.value
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
        }
    }

    suspend fun getMessage() {
        while (true) {
            try {
                val message = Requests.getMessage(_messages.value.last().id)
                _messages.value = _messages.value + message
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            } finally {
                delay(5000L)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSendClick() = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (_message.value.isBlank()) throw MessengerException("Введите сообщение")
            if (_message.value.length > 255) throw MessengerException("Ваше сообщение слишком длинное! Ограничение 255 символов")
            Requests.setMessage(message = MyMessage(_message.value))
            _scroll.value = !_scroll.value
            _message.value = ""
        } catch (e: MessengerException) {
            _error.value = e.message ?: "Ошибка"
            _dialogState.value = true
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
        }
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }
}