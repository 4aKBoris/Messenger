package com.example.messenger.ui.screens.main.chat.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.messenger.`typealias`.BoolFun
import com.example.messenger.`typealias`.Fun
import com.example.messenger.data.FullUser
import com.example.messenger.network.HttpClient
import com.example.messenger.ui.screens.main.chat.drawer.alertdialog.DialogAbout
import com.example.messenger.ui.screens.main.chat.drawer.alertdialog.DialogSettings
import com.example.messenger.ui.theme.*

@Composable
fun Drawer(
    user: FullUser,
    onDelete: Fun,
    onChangeData: Fun,
    onChangePassword: Fun,
    dialogSettings: Boolean,
    dialogAbout: Boolean,
    onChangeDialogSettings: BoolFun,
    onChangeDialogAbout: BoolFun
) {

    Header(user = user)

    Body(
        onDelete,
        onChangeData,
        onChangePassword,
        dialogSettings,
        dialogAbout,
        onChangeDialogSettings,
        onChangeDialogAbout
    )

}

@Composable
private fun Header(user: FullUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TelegramBlue),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
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
                .padding(all = 32.dp)
                .size(size = 96.dp)
                .clip(CircleShape)
                .background(color = TelegramBlue.copy(red = 0.8f))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${user.firstName} ${user.lastName ?: ""}",
            modifier = Modifier.padding(start = 32.dp)
        )
        Text(
            text = getPhoneNumber(phoneNumber = user.phoneNumber),
            modifier = Modifier.padding(start = 32.dp),
            style = Typography.body2,
            color = LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Body(
    onDelete: Fun,
    onChangeData: Fun,
    onChangePassword: Fun,
    dialogSettings: Boolean,
    dialogAbout: Boolean,
    onChangeDialogSettings: BoolFun,
    onChangeDialogAbout: BoolFun
) {

    DialogSettings(
        dialogState = dialogSettings,
        onClose = { onChangeDialogSettings(false) },
        onDelete = onDelete,
        onChangeData = onChangeData,
        onChangePassword = onChangePassword
    )

    DialogAbout(dialogState = dialogAbout) {
        onChangeDialogAbout(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Item(imageVector = Icons.Filled.Settings, name = "Настройки") {
            onChangeDialogSettings(true)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = LightGray.copy(alpha = 0.8f))
        )

        Item(imageVector = Icons.Default.Queue, name = "О приложении") {
            onChangeDialogAbout(true)
        }
    }
}

@Composable
private fun Item(imageVector: ImageVector, name: String, onClick: Fun) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(width = 16.dp))
        Icon(
            imageVector = imageVector,
            contentDescription = name,
            modifier = Modifier.size(36.dp),
            tint = LightGray
        )
        Spacer(modifier = Modifier.width(width = 16.dp))
        Text(text = name, style = ChatNameStyle, color = Black)
    }
}

private fun getPhoneNumber(phoneNumber: String): String {
    var result = "+"
    for (i in phoneNumber.indices) {
        when (i) {
            1 -> result += " (${phoneNumber[i]}"
            3 -> result += "${phoneNumber[i]}) "
            6, 8 -> result += "${phoneNumber[i]}-"
            else -> result += phoneNumber[i]
        }
    }
    return result
}