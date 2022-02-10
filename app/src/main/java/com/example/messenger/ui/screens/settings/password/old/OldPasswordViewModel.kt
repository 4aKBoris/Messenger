package com.example.messenger.ui.screens.settings.password.old

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.PasswordScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest
import java.util.*

class OldPasswordViewModel : ViewModel() {

    private val _password = mutableStateOf("")
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPassword(navController: NavController) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (_password.value.isBlank()) throw MessengerException("Введите старый пароль!")
                val digestPassword = getString(getDigest(_password.value))
                val flag = Requests.checkPassword(digestPassword)
                if (!flag) throw MessengerException("Старый пароль введён неверно!")
                withContext(Dispatchers.Main) {
                    navController.navigate(PasswordScreens.NewPassword.createRoute(password = _password.value))
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

        @RequiresApi(Build.VERSION_CODES.O)
        private fun getString(password: ByteArray) = Base64.getEncoder().encodeToString(password)

        private const val digestAlgorithm = "SHA-256"
    }
}