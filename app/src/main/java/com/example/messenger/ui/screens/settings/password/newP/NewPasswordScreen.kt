@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.settings.password.newP

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
fun NewPasswordScreen(
    data: LoginData,
    viewModel: NewPasswordViewModel,
    navController: NavController
) {

    val focusManager = LocalFocusManager.current

    val password1 by remember { viewModel.password1 }

    val password2 by remember { viewModel.password2 }

    val (visibility1, setVisibility1) = remember { mutableStateOf(false) }

    val (visibility2, setVisibility2) = remember { mutableStateOf(false) }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val keyboardController = LocalSoftwareKeyboardController.current

    val textFieldType1 = TextFieldType.Password(
        placeholderText = "Новый пароль",
        passwordVisibility = visibility1,
        setPasswordVisibility = setVisibility1,
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    val textFieldType2 = TextFieldType.Password(
        placeholderText = "Повторите пароль",
        passwordVisibility = visibility2,
        setPasswordVisibility = setVisibility2,
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    SettingsScreen(
        focusManager = focusManager,
        onClick = {
            viewModel.checkPassword(
                navController = navController,
                data = data
            )
        },
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog,
        imageVector = Icons.Filled.NavigateNext
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldScreen(
                text = password1,
                setText = viewModel::onChangePassword1,
                type = textFieldType1
            )

            TextFieldScreen(
                text = password2,
                setText = viewModel::onChangePassword2,
                type = textFieldType2
            )
        }
    }
}