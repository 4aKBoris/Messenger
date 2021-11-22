package com.example.messenger.ui.elements.alertdialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.messenger.ui.theme.Black
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.Typography

@Composable
fun AlertDialogBuilder(
    type: AlertDialogType
) {
    if (type.dialogState) {
        AlertDialog(
            onDismissRequest = type.closeDialog,
            title = { Text(text = "ДД INK Corporation", style = Typography.body1) },
            text = { Text(text = type.error, style = Typography.body2, color = Black) },
            confirmButton = {
                IconButton(
                    onClick = type.closeDialog,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    TextAlertDialogButton(text = "OK")
                }
            },
            dismissButton = { type.DismissButton() },
            shape = RoundedCornerShape(size = 16.dp)
        )
    }
}

@androidx.compose.runtime.Composable
fun TextAlertDialogButton(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        color = TelegramBlue,
        fontWeight = FontWeight.W400
    )
}