@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.settings.password.old

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messenger.data.LoginData
import com.example.messenger.ui.elements.screen.SettingsScreen
import com.example.messenger.ui.elements.textfield.TextFieldScreen
import com.example.messenger.ui.elements.textfield.TextFieldType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OldPasswordScreen(data: LoginData, viewModel: OldPasswordViewModel, navController: NavController) {

    val focusManager = LocalFocusManager.current

    val password by remember { viewModel.password }

    val (visibility, setVisibility) = remember { mutableStateOf(false) }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val keyboardController = LocalSoftwareKeyboardController.current

    val textFieldType = TextFieldType.Password(
        placeholderText = "Старый пароль",
        passwordVisibility = visibility,
        setPasswordVisibility = setVisibility,
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    SettingsScreen(
        error = error,
        dialogState = dialogState,
        closeDialog = viewModel::onCloseDialog,
        imageVector = Icons.Rounded.ArrowForward,
        focusManager = focusManager,
        onClick = { viewModel.checkPassword(navController = navController, data = data) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TextFieldScreen(
                text = password,
                setText = viewModel::onChangePassword,
                type = textFieldType
            )
        }
    }
}