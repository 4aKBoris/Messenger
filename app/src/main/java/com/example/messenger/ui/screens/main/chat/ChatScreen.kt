@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.main.chat

import androidx.animation.FastOutSlowInEasing
import androidx.animation.LinearOutSlowInEasing
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.messenger.`typealias`.Fun
import com.example.messenger.R
import com.example.messenger.`typealias`.StrFun
import com.example.messenger.messages.Message
import com.example.messenger.ui.theme.*

@Composable
fun ChatScreen(id: Int, viewModel: ChatViewModel) {

    val (message, setMessage) = remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("48 участников") },
        bottomBar = {
            BottomBar(message = message, setMessage = setMessage)
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Задний фон",
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
private fun Content(modifier: Modifier, id: Int, users: Map<Int, Pair<String, Painter>>, messages: List<Message>) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(messages) {

        }
    }
}

@Composable
private fun TopBar(countMembers: String) {
    TopAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = TelegramBlue) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {

            val (menuIcon, infoIcon, chatIcon, chatName, charCountMembers) = createRefs()

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(menuIcon) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Открыть меню",
                    tint = White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.door),
                contentDescription = "Иконка беседы",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .constrainAs(chatIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(menuIcon.end, 16.dp)
                    }
            )

            Text(
                text = "Общая беседа",
                style = ChatNameStyle,
                modifier = Modifier.constrainAs(chatName) {
                    top.linkTo(parent.top, 6.dp)
                    start.linkTo(chatIcon.end, 16.dp)
                })

            Text(
                text = countMembers,
                style = MembersCountStyle,
                modifier = Modifier.constrainAs(charCountMembers) {
                    bottom.linkTo(parent.bottom, 8.dp)
                    start.linkTo(chatIcon.end, 16.dp)
                })

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(infoIcon) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Информация о беседе",
                    tint = White
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BottomBar(message: String, setMessage: StrFun) {
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
                    icon = painterResource(id = R.drawable.ic_round_sentiment_satisfied_alt_32),
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
                            icon = painterResource(id = R.drawable.ic_round_attach_file_32),
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
                                    icon = painterResource(id = R.drawable.ic_round_mic_32),
                                    description = "Аудиосообщение",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        } else {
                            ButtonIconBottomBar(
                                icon = painterResource(id = R.drawable.ic_baseline_send_32),
                                description = "Отправть сообщение",
                                tint = TelegramBlue,
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
private fun Message(text: String, time: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TelegramBlue),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(size = 16.dp)
                    )
                    .widthIn(min = 64.dp, max = 256.dp)
                    .background(White)
            ) {
                Text(
                    text = text,
                    style = Typography.body2,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 12.dp, end = 12.dp, top = 8.dp),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = time,
                    style = Time,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp, bottom = 4.dp)
                )
            }
        }
    }

}

@Composable
private fun ButtonIconBottomBar(
    icon: Painter,
    description: String,
    modifier: Modifier = Modifier,
    tint: Color = LightLightGray,
    onClick: Fun = {}
) {
    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(painter = icon, contentDescription = description, tint = tint)
    }
}

@Preview
@Composable
private fun TopBarTest() {
    TopBar(countMembers = "48 участников")
}
