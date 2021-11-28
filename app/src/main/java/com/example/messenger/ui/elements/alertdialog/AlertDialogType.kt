package com.example.messenger.ui.elements.alertdialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.messenger.`typealias`.Fun

sealed class AlertDialogType(
    val dialogState: Boolean,
    val error: String,
    val closeDialog: Fun
) {

    @Composable
    open fun DismissButton() {
    }

    class Ordinary(dialogState: Boolean, error: String, closeDialog: Fun) :
        AlertDialogType(dialogState = dialogState, error = error, closeDialog = closeDialog)

    class Error(
        dialogState: Boolean,
        error: String,
        closeDialog: Fun,
        private val importantError: Boolean,
        private val buttonText: String,
        private val onClick: Fun
    ) :
        AlertDialogType(dialogState = dialogState, error = error, closeDialog = closeDialog) {

        @Composable
        override fun DismissButton() {
            if (importantError)
                Box(
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f).padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth(fraction = 1f)
                    ) {
                        TextAlertDialogButton(text = buttonText)
                    }
                }
        }
    }

}
