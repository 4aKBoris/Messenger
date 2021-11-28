package com.example.messenger.ui.elements.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.messenger.`typealias`.CompFun
import com.example.messenger.`typealias`.Fun
import com.example.messenger.ui.elements.alertdialog.AlertDialogBuilder
import com.example.messenger.ui.elements.alertdialog.AlertDialogType
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.White

@Composable
fun Screen(alertDialogType: AlertDialogType, onClick: Fun, focusManager: FocusManager, content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            FloatingActionButton(onClick = onClick)
        },
        content = { MainWindow(content = content, alertDialogType = alertDialogType, focusManager = focusManager) },
    )
}

@Composable
private fun MainWindow(content: CompFun, alertDialogType: AlertDialogType, focusManager: FocusManager) {
    Box(modifier = Modifier.fillMaxSize().noRippleClickable { focusManager.clearFocus(force = true) }, contentAlignment = Alignment.Center) {
        AlertDialogBuilder(type = alertDialogType)
        content()
    }
}

@Composable
private fun FloatingActionButton(onClick: Fun) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(TelegramBlue)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Продолжить",
                tint = White,
            )
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

