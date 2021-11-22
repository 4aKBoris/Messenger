@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.elements.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import com.example.messenger.StrFun
import com.example.messenger.ui.theme.Black
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.Typography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldScreen(text: String?, setText: StrFun, type: TextFieldType) {

    TextField(
        value = text?: "",
        onValueChange = setText,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Black,
            focusedBorderColor = TelegramBlue
        ),
        leadingIcon = type.leadingIcon,
        trailingIcon = type.trailingIcon,
        keyboardActions = KeyboardActions(
            onDone = { type.keyboardController?.hide() }
        ),
        placeholder = { Text(text = type.placeholderText, style = Typography.body2) },
        textStyle = Typography.body1,
        keyboardOptions = KeyboardOptions(
            autoCorrect = type.autoCorrect,
            keyboardType = type.keyboardType,
            imeAction = ImeAction.Done
        ),
        visualTransformation = type.visualTransformation,
        modifier = type.modifier
    )
}
