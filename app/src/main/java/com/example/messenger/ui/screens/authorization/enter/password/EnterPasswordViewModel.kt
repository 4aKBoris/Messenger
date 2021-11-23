package com.example.messenger.ui.screens.authorization.enter.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.exception.MessengerException
import com.example.messenger.network.Requests
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

class EnterPasswordViewModel : ViewModel() {

    private val _password = mutableStateOf("127485ldaLDA")
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
                Requests.authorization(phoneNumber = phoneNumber, password = _password.value, navController = navController)
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            } catch (e: ClientRequestException) {
                _dialogState.value = true
                _error.value = "Авторизация неудачна, введён неверный пароль!"
            } catch (e: Exception) {
                println(e.toString())
            } finally {
                _progress.value = false
            }
        }
}