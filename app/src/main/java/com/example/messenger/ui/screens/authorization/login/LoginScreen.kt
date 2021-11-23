@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.authorization.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messenger.navigation.screens.RegistrationScreens
import com.example.messenger.ui.elements.alertdialog.AlertDialogType
import com.example.messenger.ui.elements.progressbar.ProgressBar
import com.example.messenger.ui.elements.screen.Screen
import com.example.messenger.ui.elements.textfield.TextFieldScreen
import com.example.messenger.ui.elements.textfield.TextFieldType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {

    val focusManager = LocalFocusManager.current

    val phoneNumber by remember { viewModel.phoneNumber }

    val progress by remember { viewModel.progress }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val importantError by remember { viewModel.importantError }

    val keyboardController = LocalSoftwareKeyboardController.current

    val alertDialogType = AlertDialogType.Error(
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog,
        importantError = importantError,
        buttonText = "Зарегистрироваться",
        onClick = {
            viewModel.onCloseDialog()
            navController.navigate(
                RegistrationScreens.CreatePassword.createRoute(
                    phoneNumber = phoneNumber
                )
            )
        }
    )

    val textFieldType = TextFieldType.PhoneNumber(
        placeholderText = "Номер телефона",
        keyboardController = keyboardController, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    Screen(
        alertDialogType = alertDialogType,
        focusManager = focusManager,
        onClick = { viewModel.checkPhoneNumber(navController = navController) }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            TextFieldScreen(
                text = phoneNumber,
                setText = viewModel::onChangePhoneNumber,
                type = textFieldType
            )
            ProgressBar(
                progress = progress,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
