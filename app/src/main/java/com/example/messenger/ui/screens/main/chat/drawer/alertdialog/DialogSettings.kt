package com.example.messenger.ui.screens.main.chat.drawer.alertdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.messenger.`typealias`.Fun
import com.example.messenger.ui.theme.Black
import com.example.messenger.ui.theme.LightGray
import com.example.messenger.ui.theme.Typography
import com.example.messenger.ui.theme.White

@Composable
fun DialogSettings(
    dialogState: Boolean,
    onClose: Fun,
    onDelete: Fun,
    onChangeData: Fun,
    onChangePassword: Fun
) {
    if (dialogState)
        Dialog(
            onDismissRequest = onClose
        ) {
            Body(
                onDelete = onDelete,
                onChangeData = onChangeData,
                onChangePassword = onChangePassword
            )
        }
}

@Composable
private fun Body(onDelete: Fun, onChangeData: Fun, onChangePassword: Fun) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp, shape = CircleShape, clip = false),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Item(name = "Изменить учётные данные", color = Black, onClick = onChangeData)

        SpacerOne()

        Item(name = "Сменить пароль", color = Black, onClick = onChangePassword)

        SpacerOne()

        Item(name = "Удалить аккаунт", color = Color.Red, onClick = onDelete)
    }
}

@Composable
private fun Item(name: String, color: Color, onClick: Fun) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(White, Black),
                    tileMode = TileMode.Decal,
                )
            )
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = name, color = color, style = Typography.body2)
    }
}

@Composable
private fun SpacerOne() = Spacer(
    modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(Black, LightGray),
                tileMode = TileMode.Clamp
            ), alpha = 0.8f
        )
)
