package com.example.messenger.ui.screens.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.ui.screens.Screen
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommonViewModel : ViewModel() {

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
        navController: NavController,
        screen: Screen.AuthenticationScreen
    ) = viewModelScope.launch(Dispatchers.Default) {
        _progress.value = true

        if (_phoneNumber.value.length != 12) {
            _dialogState.value = true
            _error.value = "Телефонный номер введён некорректно!"
        } else {

            val check: Boolean?
            val client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }

            check = client.post<Boolean> {
                url("http://192.168.1.154:8081/checkPhoneNumber")
                contentType(ContentType.Application.Json)
                body = _phoneNumber.value
            }

            check(
                check = check,
                navController = navController,
                screen = screen
            )
        }

        _progress.value = false

    }

    private suspend fun check(
        check: Boolean?,
        navController: NavController,
        screen: Screen.AuthenticationScreen
    ) {
        when (check) {
            null -> {
                _dialogState.value = true
                _error.value = "Не удалось установить соединение с сервером! Попробуйте ещё раз"
            }
            screen.check -> {
                _dialogState.value = true
                _error.value = screen.errorMessage
                _importantError.value = true
            }
            else -> withContext(Dispatchers.Main) {
                screen.nextScreen(
                    navController = navController,
                    phoneNumber = _phoneNumber.value
                )
            }
        }
    }
}