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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.messenger.R
import com.example.messenger.`typealias`.BoolFun
import com.example.messenger.`typealias`.Fun
import com.example.messenger.data.FullUser
import com.example.messenger.data.Message
import com.example.messenger.data.User
import com.example.messenger.network.HttpClient
import com.example.messenger.network.Requests
import com.example.messenger.ui.screens.main.chat.bar.bottom.BottomBar
import com.example.messenger.ui.screens.main.chat.bar.top.TopBar
import com.example.messenger.ui.screens.main.chat.drawer.Drawer
import com.example.messenger.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaLocalDateTime
import java.net.ConnectException
import java.time.format.DateTimeFormatter

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavController, viewModel: ChatViewModel) {

    val usersCount = viewModel.users.value.size

    val scope = rememberCoroutineScope()

    var user by remember {
        mutableStateOf(
            FullUser(
                id = -2,
                phoneNumber = "79000000000",
                firstName = "Никита",
                lastName = "Архипов"
            )
        )
    }

    LaunchedEffect(key1 = true, block = {
        withContext(Dispatchers.IO) {
            user = viewModel.getUserInfo()?: user
            viewModel.getUsers()
            viewModel.getMessages()
            viewModel.getMessage()
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

    val dialogSettings by remember { viewModel.dialogSettings }

    val dialogAbout by remember { viewModel.dialogAbout }

    val users by remember { viewModel.users }

    val dialogInfo by remember { viewModel.dialogInfo }

    Scaffold(modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                countMembers = usersCount,
                openDrawer = openDrawer,
                onChangeDialogInfo = viewModel::onChangeDialogInfo
            )
        },
        bottomBar = {
            BottomBar(
                message = message,
                setMessage = viewModel::onChangeMessage,
                onSendClick = viewModel::onSendClick
            )
        },
        drawerContent = {
            Drawer(
                user = user,
                onDelete = { viewModel.onDelete(navController = navController) },
                onChangeData = {
                    viewModel.onDataChangeNavigation(navController = navController)
                },
                onChangePassword = {
                    viewModel.onPasswordChangeNavigation(
                        navController = navController
                    )
                },
                dialogSettings = dialogSettings,
                dialogAbout = dialogAbout,
                onChangeDialogSettings = viewModel::onChangeDialogSettings,
                onChangeDialogAbout = viewModel::onChangeDialogAbout
            )
        }) { paddingValues ->
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

            Content(
                id = user.id,
                messages = messages,
                scroll = scroll,
                users = users,
                dialogState = dialogInfo,
                onChangeDialogInfo = viewModel::onChangeDialogInfo
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    id: Int,
    scroll: Boolean,
    users: List<User>,
    messages: List<Message>,
    dialogState: Boolean,
    onChangeDialogInfo: BoolFun
) {

    val state = rememberLazyListState()

    LaunchedEffect(key1 = scroll) {
        state.scrollToItem(messages.size)
    }

    if (dialogState) DialogInfo(users = users) {
        onChangeDialogInfo(false)
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
                    user = users.find { u -> u.id == it.userId }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private val formatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
private fun OtherMessage(message: String, time: String, user: User?) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(color = TelegramBlue.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "${HttpClient.IpAddress}/icon?id=${user?.id ?: 0}",
                    builder = {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "Аватар пользователя",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        }
        Message(text = message, time = time, userName = user?.getName() ?: "")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DialogInfo(users: List<User>, onCloseDialog: Fun) {

    Dialog(onDismissRequest = onCloseDialog) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 16.dp))
                .background(color = White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Список пользователей",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                items(users) {
                    UserProfile(user = it)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun UserProfile(user: User) {

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        var registrationData by remember {
            mutableStateOf(
                java.time.LocalDateTime.now().format(formatter)
            )
        }

        LaunchedEffect(key1 = user) {
            try {
                registrationData = Requests.getRegistrationData(user.id)
            } catch (e: ConnectException) {
            }
        }

        Image(
            painter = rememberImagePainter(
                data = "${HttpClient.IpAddress}/icon?id=${user.id}",
                builder = {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "Аватар пользователя",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(color = TelegramBlue.copy(alpha = 0.8f))
        )

        UserName(
            name = user.getName(),
            registrationData = registrationData
        )
    }
}

@Composable
private fun UserName(name: String, registrationData: String) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = name,
            style = HintStyle,
            color = TelegramBlue
        )
        Text(
            text = "Дата регистрации: \n$registrationData",
            style = Time,
        )
    }
}
