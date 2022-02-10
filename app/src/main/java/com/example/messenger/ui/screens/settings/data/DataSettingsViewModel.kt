package com.example.messenger.ui.screens.settings.data

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.messenger.data.Icon
import com.example.messenger.data.Name
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.network.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException

class DataSettingsViewModel : ViewModel() {

    private val _firstName = mutableStateOf("")
    private val _lastName = mutableStateOf<String?>(null)
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

    fun onChangeLastName(lastName: String?) {
        _lastName.value = lastName
    }

    fun onChangeImageData(uri: Uri?) {
        _imageData.value = uri
    }

    fun onOpenDialog(message: String) {
        _dialogState.value = true
        _error.value = message
    }

    fun onCloseDialog() {
        _dialogState.value = false
        _error.value = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUser(
        navController: NavController,
        context: Context
    ) =
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            try {
                _progress.value = true
                if (_firstName.value.isBlank()) throw MessengerException("Введите имя!")
                val flagName = Requests.updateName(Name(_firstName.value, lastName.value))
                val icon = _imageData.value?.let {
                    context.contentResolver.openInputStream(it)?.readBytes()
                }
                val flagIcon = Requests.updateIcon(Icon(icon))
                if (!flagName || !flagIcon) throw MessengerException("Не удалось обновить данные! Попробуйте ещё раз")
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
            } finally {
                _progress.value = false
            }
        }
}