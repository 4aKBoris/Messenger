@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.authorization.enter.password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.messenger.ui.elements.progressbar.ProgressBar
import com.example.messenger.ui.elements.textfield.TextFieldScreen
import com.example.messenger.ui.elements.textfield.TextFieldType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EnterPasswordScreen(
    phoneNumber: String,
    viewModel: EnterPasswordViewModel,
    navController: NavController
)  {

    val focusManager = LocalFocusManager.current

    val password by remember { viewModel.password }

    val (visibility, setVisibility) = remember { mutableStateOf(false) }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val progress by remember { viewModel.progress }

    val keyboardController = LocalSoftwareKeyboardController.current

    val alertDialogType = AlertDialogType.Ordinary(
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog
    )

    val textFieldType = TextFieldType.Password(
        placeholderText = "Пароль",
        passwordVisibility = visibility,
        setPasswordVisibility = setVisibility,
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    com.example.messenger.ui.elements.screen.Screen(
        alertDialogType = alertDialogType,
        focusManager = focusManager,
        onClick = { viewModel.checkPassword(navController = navController, phoneNumber = phoneNumber) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TextFieldScreen(
                text = password,
                setText = viewModel::onChangePassword,
                type = textFieldType
            )
            ProgressBar(
                progress = progress, modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(
                        Alignment.BottomCenter
                    )
            )
        }
    }
}