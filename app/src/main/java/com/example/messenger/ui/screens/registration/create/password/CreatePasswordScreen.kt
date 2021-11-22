@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.registration.create.password

import androidx.compose.foundation.layout.*
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
import com.example.messenger.ui.elements.alertdialog.AlertDialogType
import com.example.messenger.ui.elements.textfield.TextFieldScreen
import com.example.messenger.ui.elements.textfield.TextFieldType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreatePasswordScreen(
    phoneNumber: String,
    viewModel: CreatePasswordViewModel,
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

    val alertDialogType = AlertDialogType.Ordinary(
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog
    )

    val textFieldType1 = TextFieldType.Password(
        placeholderText = "Пароль",
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

    com.example.messenger.ui.elements.screen.Screen(
        alertDialogType = alertDialogType,
        focusManager = focusManager,
        onClick = {
            viewModel.checkPassword(
                navController = navController,
                phoneNumber = phoneNumber
            )
        }
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

/*@Composable
fun CreatePasswordScreen(
    phoneNumber: String,
    viewModel: CreatePasswordViewModel,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            com.example.messenger.ui.screens.authentication.FloatingActionButton(
                onClick = {
                    viewModel.checkPassword(
                        phoneNumber = phoneNumber,
                        navController = navController
                    )
                }
            )
        },
        content = { Screen(viewModel = viewModel) },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Screen(viewModel: CreatePasswordViewModel) {

    val focusManager = LocalFocusManager.current

    val password1 by remember { viewModel.password1 }

    val password2 by remember { viewModel.password2 }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    AlertDialogBuilder(
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable {
                focusManager.clearFocus(true)
            }) {

        val keyboardController = LocalSoftwareKeyboardController.current

        TextFiledPassword(
            password = password1,
            placeholderText = "Введите пароль",
            onChangePassword = viewModel::onChangePassword1,
            keyboardController = keyboardController
        )

        TextFiledPassword(
            password = password2,
            placeholderText = "Повторите пароль",
            onChangePassword = viewModel::onChangePassword2,
            keyboardController = keyboardController
        )
    }
}*/