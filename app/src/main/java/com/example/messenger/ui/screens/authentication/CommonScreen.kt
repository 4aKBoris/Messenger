@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.messenger.Fun
import com.example.messenger.ui.screens.Screen
import com.example.messenger.ui.theme.*

@Composable
fun CommonScreen(
    viewModel: CommonViewModel,
    navController: NavController,
    screen: Screen.AuthenticationScreen
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.checkPhoneNumber(
                        navController = navController,
                        screen = screen
                    )
                }
            )
        },
        content = { Screen(viewModel = viewModel, navController = navController, screen = screen) },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Screen(
    viewModel: CommonViewModel,
    navController: NavController,
    screen: Screen.AuthenticationScreen
) {

    val focusManager = LocalFocusManager.current

    val phoneNumber by remember { viewModel.phoneNumber }

    val progress by remember { viewModel.progress }

    val dialogState by remember { viewModel.dialogState }

    val error by remember { viewModel.error }

    val importantError by remember { viewModel.importantError }

    AlertDialogFun(
        dialogState = dialogState,
        error = error,
        importantError = importantError,
        buttonText = screen.buttonText,
        onClick = { screen.error(navController = navController, phoneNumber = phoneNumber) },
        closeDialog = viewModel::onCloseDialog
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable {
                focusManager.clearFocus(true)
            }) {

        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = phoneNumber,
            onValueChange = {
                when (it.length) {
                    1 -> if (it == "+") viewModel.onChangePhoneNumber(it)
                    2 -> if (it == "+7") viewModel.onChangePhoneNumber(it)
                    else -> if (it.length <= 12) viewModel.onChangePhoneNumber(it)
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Black,
                focusedBorderColor = TelegramBlue
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Введите номер телефона"
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            placeholder = { Text(text = "Введите номер телефона", style = Typography.body2) },
            textStyle = Typography.body1,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            visualTransformation = { creditCardFilter(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (progress) CircularProgressIndicator(
                color = TelegramBlue,
                strokeWidth = 5.dp,
                modifier = Modifier.size(64.dp)
            )
        }

    }
}

@Composable
private fun AlertDialogFun(
    dialogState: Boolean,
    error: String,
    importantError: Boolean,
    buttonText: String,
    onClick: Fun,
    closeDialog: Fun
) {
    if (dialogState) {
        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(text = "ДД INK Corporation", style = Typography.body1)
            },
            text = {
                Text(text = error, style = Typography.body2, color = Black)
            },
            dismissButton = {
                if (importantError)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable(onClick = onClick)
                    ) {
                        TextAlertDialogButton(text = buttonText)
                    }
            },
            confirmButton = {
                IconButton(
                    onClick = closeDialog,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    TextAlertDialogButton(text = "OK")
                }
            },
            shape = RoundedCornerShape(size = 16.dp)
        )
    }
}

@Composable
private fun TextAlertDialogButton(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        color = TelegramBlue,
        fontWeight = FontWeight.W400
    )
}

@Composable
private fun FloatingActionButton(onClick: Fun) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(TelegramBlue)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Продолжить",
                tint = White,
            )
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
private fun creditCardFilter(text: AnnotatedString): TransformedText {

    val mask = "+7 (___) ___   __   __"

    val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            when (i) {
                2 -> {
                    append(" (")
                    append(trimmed[i])
                }
                4 -> {
                    append(trimmed[i])
                    append(") ")
                }
                7, 9 -> {
                    append(trimmed[i])
                    append(" - ")

                }
                else -> append(trimmed[i])
            }
        }
        pushStyle(
            SpanStyle(
                color = LightGray,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
            )
        )
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset + 2
            if (offset <= 7) return offset + 4
            if (offset <= 9) return offset + 7
            if (offset <= 12) return offset + 10
            return 22
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 5) return offset - 2
            if (offset <= 9) return offset - 4
            if (offset <= 15) return offset - 7
            if (offset <= 20) return offset - 10
            return 12
        }
    }

    return TransformedText(annotatedString, creditCardOffsetTranslator)
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}