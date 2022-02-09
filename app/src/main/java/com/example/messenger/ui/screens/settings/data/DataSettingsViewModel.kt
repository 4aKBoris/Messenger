package com.example.messenger.ui.screens.settings.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.LoginData
import com.example.messenger.exception.MessengerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

class DataSettingsViewModel : ViewModel() {

    private val _firstName = mutableStateOf("")
    private val _lastName = mutableStateOf<String?>(null)
    private val _progress = mutableStateOf(false)
    private val _error = mutableStateOf("")
    private val _dialogState = mutableStateOf(false)
    private val _imageData = mutableStateOf<ByteArray?>(null)

    val firstName: State<String> = _firstName
    val lastName: State<String?> = _lastName
    val progress: State<Boolean> = _progress
    val error: State<String> = _error
    val dialogState: State<Boolean> = _dialogState
    val imageData: State<ByteArray?> = _imageData

    fun onChangeFirstName(firstName: String) {
        _firstName.value = firstName
    }

    fun onChangeLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun onChangeImageData(icon: ByteArray?) {
        _imageData.value = icon
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    fun updateUser(
        data: LoginData,
        navController: NavController
    ) =
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            try {
                /*_progress.value = true
                if (_firstName.value.isBlank()) throw MessengerException("Введите имя!")
                val user = User(
                    data = data,
                    DataUser(
                        firstName = _firstName.value,
                        lastName = _lastName.value,
                        icon = _imageData.value
                    )
                )
                val flag = Requests.changeData(data = user)
                if (!flag) throw MessengerException("Не удалось обновить данные! Попробуйте ещё раз")
                withContext(Dispatchers.Main) {
                    navController.backQueue.clear()
                    navController.navigate(MainScreens.Chat.createRoute(data = data))
                }*/
            } catch (e: MessengerException) {
                _dialogState.value = true
                _error.value = e.message!!
            } catch (e: ConnectException) {
                _dialogState.value = true
                _error.value = "Не удалось устаность соединение с сервером! Попробуйте ещё раз"
            } finally {
                _progress.value = false
            }
        }
}