@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.registration.user.info

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.messenger.R
import com.example.messenger.ui.elements.alertdialog.AlertDialogType
import com.example.messenger.ui.elements.progressbar.ProgressBar
import com.example.messenger.ui.elements.screen.Screen
import com.example.messenger.ui.elements.textfield.TextFieldScreen
import com.example.messenger.ui.elements.textfield.TextFieldType
import com.example.messenger.ui.theme.Black
import com.example.messenger.ui.theme.TelegramBlue

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInfoScreen(
    phoneNumber: String,
    password: String,
    viewModel: UserInfoViewModel,
    navController: NavController,
    context: Context
) {

    val focusManager = LocalFocusManager.current

    val firstName by remember { viewModel.firstName }

    val lastName by remember { viewModel.lastName }

    val progress by remember { viewModel.progress }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val keyboardController = LocalSoftwareKeyboardController.current

    val imageData by remember { viewModel.imageData }

    val selectImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.onChangeImageData(uri = uri)
        }

    val alertDialogType = AlertDialogType.Ordinary(
        dialogState = dialogState,
        error = error,
        closeDialog = viewModel::onCloseDialog
    )

    val textFieldTypeFirstName = TextFieldType.Ordinary(
        placeholderText = "Имя",
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    val textFieldTypeLastName = TextFieldType.Ordinary(
        placeholderText = "Фамилия",
        keyboardController = keyboardController,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    )

    Screen(
        alertDialogType = alertDialogType,
        focusManager = focusManager,
        onClick = {
            viewModel.registerUser(
                phoneNumber = phoneNumber,
                password = password,
                navController = navController,
                context = context
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextFieldScreen(
                        text = firstName,
                        setText = viewModel::onChangeFirstName,
                        type = textFieldTypeFirstName
                    )

                    TextFieldScreen(
                        text = lastName,
                        setText = viewModel::onChangeLastName,
                        type = textFieldTypeLastName
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = rememberImagePainter(
                            data = imageData?: R.drawable.avatar,
                            builder = {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }),
                        contentDescription = null,
                        modifier = Modifier
                            .size(256.dp)
                            .clip(shape = CircleShape)
                            .border(
                                width = 2.dp, brush = Brush.sweepGradient(
                                    colors = listOf(
                                        TelegramBlue, Black, TelegramBlue
                                    )
                                ), shape = CircleShape
                            )
                            .clickable { selectImageLauncher.launch("image/*") }
                    )
                }

                ProgressBar(
                    progress = progress,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

