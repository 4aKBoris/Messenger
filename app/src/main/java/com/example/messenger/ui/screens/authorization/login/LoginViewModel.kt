package com.example.messenger.ui.screens.authorization.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.AuthorizationScreens
import com.example.messenger.network.Requests
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException

class LoginViewModel : ViewModel() {

    private val _progress = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _phoneNumber = mutableStateOf("+7")
    private val _importantError = mutableStateOf(false)

    val progress: State<Boolean> = _progress
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState
    val phoneNumber: State<String> = _phoneNumber
    val importantError: State<Boolean> = _importantError

    fun onCloseDialog() {
        _dialogState.value = false
        _importantError.value = false
        _error.value = ""
    }

    fun onChangePhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun checkPhoneNumber(
        navController: NavController
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _progress.value = true
            if (_phoneNumber.value.length != 12) throw MessengerException("Телефонный номер введён некорректно!")
            val response = Requests.checkPhoneNumber(phoneNumber = _phoneNumber.value.removePrefix("+"))
            check(status = response.receive(), navController = navController)
        } catch (e: MessengerException) {
            _dialogState.value = true
            _error.value = e.message!!
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
        } finally {
            _progress.value = false
        }
    }

    private suspend fun check(
        status: Boolean,
        navController: NavController
    ) {
        when (status) {
            false -> {
                _dialogState.value = true
                _error.value = "На данный телефонный номер аккаунт не зарегистрирован!"
                _importantError.value = true
            }
            else -> withContext(Dispatchers.Main) {
                onCloseDialog()
                navController.navigate(AuthorizationScreens.EnterPassword.createRoute(_phoneNumber.value.removePrefix("+")))
            }
        }
    }
}