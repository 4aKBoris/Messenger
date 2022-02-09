package com.example.messenger.ui.screens.registration.user.info

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.DataUser
import com.example.messenger.data.LoginData
import com.example.messenger.data.User
import com.example.messenger.data.UserRegistration
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.util.*


class UserInfoViewModel : ViewModel() {

    private val _firstName = mutableStateOf("Дмитрий")
    private val _lastName = mutableStateOf<String?>("Лосев")
    private val _progress = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _imageData = mutableStateOf<Uri?>(null)

    val firstName: State<String> = _firstName
    val lastName: State<String?> = _lastName
    val progress: State<Boolean> = _progress
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState
    val imageData: State<Uri?> = _imageData

    fun onChangeFirstName(firstName: String) {
        _firstName.value = firstName
    }

    fun onChangeLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun onChangeImageData(uri: Uri?) {
        _imageData.value = uri
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerUser(
        data: LoginData,
        password: String,
        navController: NavController,
        context: Context
    ) =
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            try {
                _progress.value = true
                if (_firstName.value.isBlank()) throw MessengerException("Введите имя!")
                val icon = _imageData.value?.let {
                    Base64.getEncoder()
                        .encodeToString(context.contentResolver.openInputStream(it)?.readBytes())
                }
                val user = UserRegistration(
                    User(
                        data = data,
                        DataUser(firstName = _firstName.value, lastName = _lastName.value)
                    ),
                    icon = icon
                )
                val flag = Requests.registration(user = user)
                if (flag) withContext(Dispatchers.Main) {
                    navController.backQueue.clear()
                    navController.navigate(MainScreens.Chat.createRoute())
                }
                else throw MessengerException("Не удалось зарегистривроаться! Попробуйте ещё раз")
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
}