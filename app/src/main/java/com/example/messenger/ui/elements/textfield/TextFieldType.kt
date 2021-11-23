@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED", "EXPERIMENTAL_API_USAGE_FUTURE_ERROR")

package com.example.messenger.ui.elements.textfield

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.sp
import com.example.messenger.`typealias`.BoolFun
import com.example.messenger.`typealias`.CompFun
import com.example.messenger.ui.theme.DarkGreen
import com.example.messenger.ui.theme.LightGray
import com.example.messenger.ui.theme.Red

sealed class TextFieldType @OptIn(ExperimentalComposeUiApi::class) constructor(
    val placeholderText: String,
    val modifier: Modifier,
    val keyboardController: SoftwareKeyboardController?
) {

    open val keyboardType: KeyboardType
        get() = KeyboardType.Text

    open val autoCorrect: Boolean
        get() = false

    open val visualTransformation: VisualTransformation
        get() = VisualTransformation.None


    open val leadingIcon: CompFun? = null

    open val trailingIcon: CompFun? = null

    class Password @OptIn(ExperimentalComposeUiApi::class) constructor(
        placeholderText: String,
        modifier: Modifier,
        keyboardController: SoftwareKeyboardController?,
        private val passwordVisibility: Boolean,
        private val setPasswordVisibility: BoolFun
    ) : TextFieldType(placeholderText, modifier, keyboardController) {

        override val leadingIcon: CompFun
            get() = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "Пароль"
                )
            }

        override val trailingIcon: CompFun
            get() = {

                val tintColor by animateColorAsState(
                    targetValue = if (passwordVisibility) Red else DarkGreen,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )

                IconButton(onClick = { setPasswordVisibility(!passwordVisibility) }) {
                    Crossfade(
                        targetState = passwordVisibility,
                        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                    ) {
                        Icon(
                            imageVector = if (it) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = "Изменение видимости пароля",
                            tint = tintColor
                        )
                    }
                }
            }

        override val visualTransformation: VisualTransformation
            get() = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()

        override val keyboardType: KeyboardType
            get() = KeyboardType.Password
    }

    class PhoneNumber @OptIn(ExperimentalComposeUiApi::class) constructor(
        placeholderText: String,
        modifier: Modifier,
        keyboardController: SoftwareKeyboardController?
    ) :
        TextFieldType(placeholderText, modifier, keyboardController) {

        override val leadingIcon: CompFun
            get() = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Номер телефона"
                )
            }

        override val keyboardType: KeyboardType
            get() = KeyboardType.Number

        override val visualTransformation: VisualTransformation
            get() = VisualTransformation { creditCardFilter(it) }

        @OptIn(ExperimentalUnitApi::class)
        private fun creditCardFilter(text: AnnotatedString): TransformedText {

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

        companion object {
            private const val mask = "+7 (___) ___   __   __"
        }

    }

    class Ordinary @OptIn(ExperimentalComposeUiApi::class) constructor(
        placeholderText: String,
        modifier: Modifier,
        keyboardController: SoftwareKeyboardController?
    ) : TextFieldType(placeholderText, modifier, keyboardController) {

        override val autoCorrect: Boolean
            get() = true
    }
}

