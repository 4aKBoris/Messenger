package com.example.messenger.ui.screens.authorization.enter.password

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.security.MessageDigest
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkPassword(navController: NavController, phoneNumber: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _progress.value = true
                if (_password.value.isBlank()) throw MessengerException("Введите пароль!")
                Requests.authorization(name = phoneNumber, password = getString(getDigest(_password.value)))
                println(phoneNumber)
                Requests.check()
                withContext(Dispatchers.Main) {
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

        @RequiresApi(Build.VERSION_CODES.O)
        private fun getString(password: ByteArray) = Base64.getEncoder().encodeToString(password)

        private const val digestAlgorithm = "SHA-256"
    }
}