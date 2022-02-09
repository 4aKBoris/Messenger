package com.example.messenger.ui.screens.authorization.enter.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.LoginData
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest

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
                val flag = Requests.authorization(
                    data = LoginData(
                        phoneNumber = phoneNumber,
                        password = getDigest("$phoneNumber:$myRealm:${_password.value}")
                    ), password = _password.value
                )
                if (!flag) throw MessengerException("Не удалось авторизоваться! Попробуйте ещё раз")
                else withContext(Dispatchers.Main) {
                    navController.backQueue.clear()
                    navController.navigate(MainScreens.Chat.createRoute())
                }
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            } catch (e: ClientRequestException) {
                _dialogState.value = true
                _error.value = "Авторизация неудачна, введён неверный пароль!"
            } finally {
                _progress.value = false
            }
        }

    companion object {

        private fun getDigest(str: String): ByteArray =
            MessageDigest.getInstance(digestAlgorithm).digest(str.toByteArray(Charsets.UTF_8))

        private const val digestAlgorithm = "MD5"

        private const val myRealm = "RestAPI"
    }
}