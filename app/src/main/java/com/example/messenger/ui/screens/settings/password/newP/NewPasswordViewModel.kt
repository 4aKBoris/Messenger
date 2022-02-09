package com.example.messenger.ui.screens.settings.password.newP

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.Password
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest
import java.util.*

class NewPasswordViewModel : ViewModel() {

    private val _password1 = mutableStateOf("")
    private val _password2 = mutableStateOf("")
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _progress = mutableStateOf(false)

    val password1: State<String> = _password1
    val password2: State<String> = _password2
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState
    val progress: State<Boolean> = _progress

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPassword(oldPassword: String, navController: NavController) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when {
                    _password1.value.isBlank() -> throw MessengerException("Введите пароль!")
                    _password1.value != _password2.value -> throw MessengerException("Введёные пароли не совпадают!")
                    regBigSymbol.matches(_password1.value) -> throw MessengerException("В пароле должна быть хотя бы одна заглавная буква!")
                    _password1.value.length <= 7 -> throw MessengerException("Длина пароля должна быть 8 символов и больше!")
                    _password1.value == oldPassword -> throw MessengerException("Новый пароль не должен совпадать со старым!")
                    else -> {
                        val newPasswordDigest = getString(getDigest(_password1.value))
                        val flag = Requests.updatePassword(Password(newPasswordDigest))
                        if (!flag) throw MessengerException("Не удалось сменить пароль, попробуйте ещё раз!")
                        withContext(Dispatchers.Main) {
                            navController.navigate(MainScreens.Chat.createRoute())
                        }
                    }
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

        private val regBigSymbol = Regex("[A-Z]+")

        private fun getDigest(str: String): ByteArray =
            MessageDigest.getInstance(digestAlgorithm).digest(str.toByteArray(Charsets.UTF_8))

        @RequiresApi(Build.VERSION_CODES.O)
        private fun getString(password: ByteArray) = Base64.getEncoder().encodeToString(password)

        private const val digestAlgorithm = "SHA-256"
    }
}