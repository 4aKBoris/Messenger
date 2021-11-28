package com.example.messenger.ui.screens.settings.password.old

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.LoginData
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.PasswordScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest

class OldPasswordViewModel: ViewModel() {

    private val _password = mutableStateOf("127485ldaLDA")
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)

    val password: State<String> = _password
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState

    fun onChangePassword(password: String) {
        _password.value = password
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    fun checkPassword(navController: NavController, data: LoginData) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (_password.value.isBlank()) throw MessengerException("Введите старый пароль!")
                val digestPassword = getDigest("${data.phoneNumber}:$myRealm:${_password.value}")
                if (!data.password.contentEquals(digestPassword)) throw MessengerException("Старый пароль введён неверно!")
                withContext(Dispatchers.Main) {
                    navController.navigate(PasswordScreens.NewPassword.createRoute(data = data))
                }
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            }
        }

    companion object {

        private fun getDigest(str: String): ByteArray =
            MessageDigest.getInstance(digestAlgorithm).digest(str.toByteArray(Charsets.UTF_8))

        private const val digestAlgorithm = "MD5"

        private const val myRealm = "RestAPI"
    }
}