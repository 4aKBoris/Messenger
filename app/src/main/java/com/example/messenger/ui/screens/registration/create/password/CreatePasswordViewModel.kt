package com.example.messenger.ui.screens.registration.create.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.messenger.navigation.screens.RegistrationScreens

class CreatePasswordViewModel : ViewModel() {

    private val _password1 = mutableStateOf("")
    private val _password2 = mutableStateOf("")
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)

    val password1: State<String> = _password1
    val password2: State<String> = _password2
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState

    fun onChangePassword1(password: String) {
        _password1.value = password
    }

    fun onChangePassword2(password: String) {
        _password2.value = password
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    private fun onOpenDialog(error: String) {
        _dialogState.value = true
        _error.value = error
    }

    fun checkPassword(phoneNumber: String, navController: NavController) {
        when (true) {
            _password1.value.isBlank() -> onOpenDialog("Введите пароль!")
            _password1.value != _password2.value -> onOpenDialog("Введёные пароли не совпадают!")
            regBigSymbol.matches(_password1.value) -> onOpenDialog("В пароле должна быть хотя бы одна заглавная буква!")
            _password1.value.length <= 7 -> onOpenDialog("Длина пароля должна быть 8 символов и больше!")
            else -> navController.navigate(
                RegistrationScreens.UserInfo.createRoute(
                    password = _password1.value,
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    companion object {
        private val regBigSymbol = Regex("[A-Z]+")
    }
}