@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.elements.progressbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.messenger.ui.theme.TelegramBlue

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressBar(progress: Boolean, modifier: Modifier) {
    AnimatedVisibility(
        visible = progress,
        modifier = modifier,
        enter = fadeIn(
            initialAlpha = 0f,
            animationSpec = tween(durationMillis = 300, easing = LinearEasing)
        ),
        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 300, easing = LinearEasing)
        )
    ) {
        CircularProgressIndicator(
            color = TelegramBlue,
            strokeWidth = 5.dp,
            modifier = Modifier.size(64.dp)
        )
    }
}