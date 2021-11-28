package com.example.messenger.ui.elements.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.messenger.`typealias`.CompFun
import com.example.messenger.`typealias`.Fun
import com.example.messenger.ui.elements.alertdialog.AlertDialogBuilder
import com.example.messenger.ui.elements.alertdialog.AlertDialogType
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.White

@Composable
fun SettingsScreen(
    onClick: Fun,
    focusManager: FocusManager,
    dialogState: Boolean,
    error: String,
    closeDialog: Fun,
    imageVector: ImageVector,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            FloatingActionButton(onClick = onClick, imageVector = imageVector)
        },
        content = {
            MainWindow(
                dialogState = dialogState,
                error = error,
                closeDialog = closeDialog,
                content = content,
                focusManager = focusManager
            )
        },
    )
}

@Composable
private fun MainWindow(
    dialogState: Boolean,
    error: String,
    closeDialog: Fun,
    focusManager: FocusManager,
    content: CompFun,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { focusManager.clearFocus(force = true) },
        contentAlignment = Alignment.Center
    ) {
        AlertDialogBuilder(type = AlertDialogType.Ordinary(dialogState, error, closeDialog))
        content()
    }
}

@Composable
private fun FloatingActionButton(imageVector: ImageVector, onClick: Fun) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(TelegramBlue)
                .size(56.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Продолжить",
                tint = White,
            )
        }
    }
}
