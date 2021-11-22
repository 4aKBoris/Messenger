@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED", "DEPRECATION")

package com.example.messenger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messenger.navigation.Navigation
import com.example.messenger.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


const val LOG_TAG = "LOG_TAG"

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessengerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(activity = this)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("NewApi")
@Composable
private fun OtherMessages() {

    val state = rememberLazyListState()

    val list = listOf(
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32dwaaaaaaaaaaaaaaaaaaaawdaaaaaaaaaaaaaa",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
        com.example.messenger.messages.Message(
            message = "dwa523d32d32",
            id = 5,
            date = LocalDate.now(),
            LocalTime.now()
        ),
    )

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(TelegramBlue),
        state = state,
        verticalArrangement = Arrangement.Bottom,
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        items(list) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.door),
                    "dwadwa",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Message(text = it.message, time = it.time.format(formatter))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Message(text: String, time: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 16.dp))
            .widthIn(min = 64.dp, max = 224.dp)
            .background(White)
    ) {
        Text(
            text = "Дмитрий Лосев",
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
                .padding(end = 8.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MessengerTheme {
        Greeting("Android")
    }
}