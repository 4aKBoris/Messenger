package com.example.messenger.ui.screens.authorization.enter.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.MessengerException
import com.example.messenger.data.AuthorizationData
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

class EnterPasswordViewModel : ViewModel() {

    private val _password = mutableStateOf("")
    private val _progress = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)

    val password: State<String> = _password
    val progress: State<Boolean> = _progress
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState

    fun onChangePassword(password: String) {
        _password.value = password
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    fun checkPassword(navController: NavController, phoneNumber: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _progress.value = true
                if (_password.value.isBlank()) throw MessengerException("Введите пароль!")
                val data = AuthorizationData(phoneNumber = phoneNumber, password = _password.value)
                val post = Requests.authorization(data = data)
                println(post)
                //if (!post) throw MessengerException("Не удалось авторизоваться! Попробуйте ещё раз")
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            } catch (e: Exception) {
                println(e.message)
            } finally {
                _progress.value = false
            }
        }
}