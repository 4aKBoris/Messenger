@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.main.chat.bar.bottom

import androidx.animation.FastOutSlowInEasing
import androidx.animation.LinearOutSlowInEasing
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.SentimentSatisfiedAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messenger.`typealias`.Fun
import com.example.messenger.`typealias`.StrFun
import com.example.messenger.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(message: String, setMessage: StrFun, onSendClick: Fun) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        TextField(
            value = message, onValueChange = setMessage,
            placeholder = {
                Text(text = "Сообщение", style = Typography.body2, fontSize = 18.sp)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = White,
                cursorColor = TelegramBlue,
                placeholderColor = LightGray,
                focusedBorderColor = White
            ),
            leadingIcon = {
                ButtonIconBottomBar(
                    icon = Icons.Rounded.SentimentSatisfiedAlt,
                    description = "Выбор смайла",
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End,
                ) {

                    val density = LocalDensity.current

                    AnimatedVisibility(
                        visible = message.isBlank(),
                        enter = slideInHorizontally(initialOffsetX = {
                            with(density) { 44.dp.roundToPx() }
                        }, animationSpec = tween(500, easing = LinearOutSlowInEasing)) + fadeIn(
                            initialAlpha = 0f,
                            animationSpec = tween(500, easing = FastOutSlowInEasing)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { with(density) { 44.dp.roundToPx() } },
                            animationSpec = tween(500, easing = LinearOutSlowInEasing)
                        ) + fadeOut(
                            targetAlpha = 0f,
                            animationSpec = tween(500, easing = FastOutSlowInEasing)
                        )
                    ) {
                        ButtonIconBottomBar(
                            icon = Icons.Rounded.AttachFile,
                            description = "Выбор файла",
                            modifier = Modifier.rotate(225f)
                        )
                    }

                    Crossfade(
                        targetState = message.isBlank(),
                        animationSpec = tween(500, easing = FastOutLinearInEasing)
                    ) {
                        if (it) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.End,
                            ) {
                                ButtonIconBottomBar(
                                    icon = Icons.Rounded.Mic,
                                    description = "Аудиосообщение",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        } else {
                            ButtonIconBottomBar(
                                icon = Icons.Rounded.Send,
                                description = "Отправть сообщение",
                                tint = TelegramBlue,
                                size = 24.dp,
                                onClick = onSendClick,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                }
            },
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ButtonIconBottomBar(
    icon: ImageVector,
    description: String,
    modifier: Modifier = Modifier,
    tint: Color = LightLightGray,
    size: Dp = 36.dp,
    onClick: Fun = {}
) {
    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = tint,
            modifier = Modifier.size(size = size)
        )
    }
}