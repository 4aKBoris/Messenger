package com.example.messenger.ui.screens.main.chat.drawer.alertdialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.messenger.R
import com.example.messenger.`typealias`.Fun
import com.example.messenger.ui.theme.LightGray
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.Typography

@Composable
fun DialogAbout(dialogState: Boolean, onClose: Fun) {
    if (dialogState) Dialog(onDismissRequest = onClose) {
        Body()
    }
}


@Composable
private fun Body() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TelegramBlue, shape = RoundedCornerShape(size = 16.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.door),
            contentDescription = "Иконка приложения",
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 32.dp)
                .size(size = 96.dp)
                .clip(shape = RoundedCornerShape(size = 24.dp))
        )
        Item(name = "Версия", "1.0.0")
        Item(name = "Название", "Messenger")
        Item(name = "Разработчик", "Лосев Дмитрий")
        Item(name = "Группа", "А-13м-21")
        Spacer(modifier = Modifier.height(height = 32.dp))
    }
}

@Composable
private fun Item(name: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name)
        Text(text = text, color = LightGray, style = Typography.body2)
    }
}