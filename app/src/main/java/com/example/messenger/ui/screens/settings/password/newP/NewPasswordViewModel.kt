package com.example.messenger.ui.screens.settings.password.newP

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.ChangePassword
import com.example.messenger.data.LoginData
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest

class NewPasswordViewModel : ViewModel() {

    private val _password1 = mutableStateOf("127485ldaLD")
    private val _password2 = mutableStateOf("127485ldaLD")
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

    fun checkPassword(data: LoginData, navController: NavController) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (true) {
                    _password1.value.isBlank() -> throw MessengerException("Введите пароль!")
                    _password1.value != _password2.value -> throw MessengerException("Введёные пароли не совпадают!")
                    regBigSymbol.matches(_password1.value) -> throw MessengerException("В пароле должна быть хотя бы одна заглавная буква!")
                    _password1.value.length <= 7 -> throw MessengerException("Длина пароля должна быть 8 символов и больше!")
                    getDigest("${data.phoneNumber}:$myRealm:${_password1.value}").contentEquals(data.password) -> throw MessengerException(
                        "Новый пароль не должен совпадать со старым!"
                    )
                    else -> {
                        val newPasswordDigest =
                            getDigest("${data.phoneNumber}:$myRealm:${_password1.value}")
                        val changePassword =
                            ChangePassword(data = data, newPassword = newPasswordDigest)
                        val flag = Requests.changePassword(changePassword = changePassword)
                        if (!flag) throw MessengerException("Не удалось сменить пароль, попробуйте ещё раз!")
                        else withContext(Dispatchers.Main) {
                            navController.navigate(MainScreens.Chat.createRoute(data = data.copy(password = newPasswordDigest)))
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

        private const val digestAlgorithm = "MD5"

        private const val myRealm = "RestAPI"
    }
}