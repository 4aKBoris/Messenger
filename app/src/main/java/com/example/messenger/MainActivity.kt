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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messenger.navigation.Navigation
import com.example.messenger.ui.theme.MessengerTheme
import com.example.messenger.ui.theme.TelegramBlue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
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

@Composable
private fun SystemMessage(message: String, time: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Задний фон",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.door), contentDescription = "Аватар пользователя",
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                )
                /*Message(
                    text = message,
                    time = time
                )*/
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
        com.example.messenger.data.Message(
            message = "dwa523d32d32",
            userId = 5,
            dateTime = LocalDateTime.now().toKotlinLocalDateTime()
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

            Spacer(modifier = Modifier.height(16.dp))
        }
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