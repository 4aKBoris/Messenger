package com.example.messenger.ui.screens.registration.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.MessengerException
import com.example.messenger.navigation.screens.RegistrationScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException

class RegisterViewModel : ViewModel() {

    private val _progress = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _phoneNumber = mutableStateOf("+79255696413")
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
            val check = Requests.checkPhoneNumber(phoneNumber = _phoneNumber.value)
            check(check = check, navController = navController)
        } catch (e: MessengerException) {
            _dialogState.value = true
            _error.value = e.message!!
        } catch (e: ConnectException) {
            _dialogState.value = true
            _error.value = "Не удалось установить соединение с сервером! Попробуйте ещё раз"
        } finally {
            _progress.value = false
        }

    }

    private suspend fun check(
        check: Boolean,
        navController: NavController
    ) {
        when (check) {
            true -> {
                _dialogState.value = true
                _error.value = "На данный телефонный номер аккаунт уже зарегистрирован!"
                _importantError.value = true
            }
            false -> withContext(Dispatchers.Main) {
                navController.navigate(RegistrationScreens.CreatePassword.createRoute(_phoneNumber.value))
            }
        }
    }
}