package com.example.messenger.ui.screens.main.chat

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messenger.R
import com.example.messenger.`typealias`.IntFunStr
import com.example.messenger.data.Message
import com.example.messenger.ui.screens.main.chat.bar.bottom.BottomBar
import com.example.messenger.ui.screens.main.chat.bar.top.TopBar
import com.example.messenger.ui.screens.main.chat.drawer.Drawer
import com.example.messenger.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(id: Int, viewModel: ChatViewModel) {

    val usersCount = viewModel.users.value.size

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = id, block = {
        withContext(Dispatchers.IO) {
            viewModel.getUsers()
            viewModel.getMessages()
            viewModel.getMessage(id = id)
        }
    })

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scaffoldState = rememberScaffoldState(drawerState = drawerState)

    val openDrawer = fun() {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    val messages by remember { viewModel.messages }

    val message by remember { viewModel.message }

    val scroll by remember { viewModel.scroll }

    Scaffold(modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBar(countMembers = usersCount, openDrawer = openDrawer) },
        bottomBar = {
            BottomBar(
                message = message,
                setMessage = viewModel::onChangeMessage,
                onSendClick = { viewModel.onSendClick(id = id) })
        },
        drawerContent = { Drawer() }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Задний фон",
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds
            )

            Content(id = id, messages = messages, scroll = scroll, getName = viewModel::getUserName)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    id: Int,
    scroll: Boolean,
    getName: IntFunStr,
    messages: List<Message>
) {

    val state = rememberLazyListState()

    LaunchedEffect(key1 = scroll) {
        state.scrollToItem(messages.size)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.Bottom,
        state = state
    ) {
        items(messages) {
            val time = it.dateTime.toJavaLocalDateTime().toLocalTime().format(formatter)
            when (it.userId) {
                -1 -> SystemMessage(message = it.message)
                id -> YourMessage(message = it.message, time = time)
                else -> OtherMessage(
                    message = it.message,
                    time = time,
                    userName = getName(it.userId)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private val formatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
private fun OtherMessage(message: String, time: String, userName: String) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.door),
            contentDescription = "Аватар пользователя",
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .size(48.dp)
                .clip(CircleShape)
        )
        Message(text = message, time = time, userName = userName)
    }
}

@Composable
private fun Message(text: String, time: String, userName: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 16.dp))
            .widthIn(min = 64.dp, max = 256.dp)
            .background(White)
    ) {
        Text(
            text = userName,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp, end = 12.dp, top = 8.dp),
            style = HintStyle,
            color = TelegramBlue
        )
        Text(
            text = text,
            style = Typography.body2,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp, end = 12.dp),
            textAlign = TextAlign.Start,
        )
        Text(
            text = time,
            style = Time,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 12.dp, bottom = 4.dp)
        )
    }
}

@Composable
private fun YourMessage(message: String, time: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(size = 16.dp))
                .widthIn(min = 64.dp, max = 256.dp)
                .background(White),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = message,
                style = Typography.body2,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = time,
                style = Time,
                modifier = Modifier
                    .padding(bottom = 4.dp, end = 12.dp)
                    .align(Alignment.End)
            )
        }
    }

}

@Composable
private fun SystemMessage(message: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(SystemMessage), contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                color = White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600
            )
        }
    }
}
