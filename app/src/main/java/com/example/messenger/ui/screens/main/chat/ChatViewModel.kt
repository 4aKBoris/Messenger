package com.example.messenger.ui.screens.main.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.ChatUser
import com.example.messenger.data.LoginData
import com.example.messenger.data.Message
import com.example.messenger.data.User
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.navigation.screens.PasswordScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toKotlinLocalDateTime
import java.net.ConnectException
import java.time.LocalDateTime
import kotlin.math.max

class ChatViewModel : ViewModel() {

    private val _message = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _users = mutableStateOf(listOf<ChatUser>())
    private val _messages = mutableStateOf(listOf<Message>())
    private val _scroll = mutableStateOf(false)
    private val _dialogSettings = mutableStateOf(false)
    private val _dialogAbout = mutableStateOf(false)
    private val _dialogInfo = mutableStateOf(false)

    val message: State<String> = _message
    val dialogState: State<Boolean> = _dialogState
    val error: State<String> = _error
    val users: State<List<ChatUser>> = _users
    val messages: State<List<Message>> = _messages
    val scroll: State<Boolean> = _scroll
    val dialogSettings: State<Boolean> = _dialogSettings
    val dialogAbout: State<Boolean> = _dialogAbout
    val dialogInfo: State<Boolean> = _dialogInfo

    private var max = 0

    fun onChangeDialogSettings(state: Boolean) {
        _dialogSettings.value = state
    }

    fun onChangeDialogAbout(state: Boolean) {
        _dialogAbout.value = state
    }

    fun onChangeDialogInfo(state: Boolean) {
        _dialogInfo.value = state
    }

    suspend fun getUserInfo() = Requests.getUserInfo()

    fun onDelete(loginData: LoginData, navController: NavController) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val flag = Requests.deleteUser(data = loginData)
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
        data: LoginData
    ) {
        navController.navigate(PasswordScreens.OldPassword.createRoute(data = data))
        onChangeDialogSettings(state = false)
    }

    fun onDataChangeNavigation(
        navController: NavController,
        user: User
    ) {
        //navController.navigate(MainScreens.SettingsData.createRoute(user = user.copy(dataUser = user.dataUser.copy(icon = null))))
        onChangeDialogSettings(state = false)
    }

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

    suspend fun getMessage() {
        while (true) {
            max = max(max, _messages.value.last().id!!)
            val message = Requests.getMessage(max)
            _messages.value = _messages.value + message
            delay(5000L)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSendClick(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (_message.value.isBlank()) throw MessengerException("Введите сообщение")
            if (_message.value.length > 255) throw MessengerException("Ваше сообщение слишком длинное! Ограничение 255 символов")
            val mess = Message(
                null,
                message = _message.value,
                userId = id,
                dateTime = LocalDateTime.now().toKotlinLocalDateTime()
            )
            val idMessage = Requests.setMessage(message = mess)
            max++
            _messages.value = _messages.value + mess.copy(id = idMessage)
            _scroll.value = !_scroll.value
            _message.value = ""
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