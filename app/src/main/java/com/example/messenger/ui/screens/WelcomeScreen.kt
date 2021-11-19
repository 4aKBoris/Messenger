@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.messenger.R
import com.example.messenger.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (icon, registration, hint, enter) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.door),
            contentDescription = "ДД",
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(size = 16.dp))
                .constrainAs(icon) {
                    top.linkTo(parent.top, 128.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            onClick = { navController.navigate(Screen.AuthenticationScreen.Register.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(size = 8.dp))
                .background(DarkGreen)
                .height(44.dp)
                .constrainAs(registration) {
                    bottom.linkTo(hint.top, 84.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        ) {
            Text(text = "Зарегистрироваться", color = White, style = Typography.button)
        }

        Text(text = "Уже есть аккаунт?", style = HintStyle, modifier = Modifier.constrainAs(hint) {
            bottom.linkTo(enter.top, 24.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        IconButton(
            onClick = { navController.navigate(Screen.AuthenticationScreen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(size = 8.dp))
                .background(TelegramBlue)
                .height(44.dp)
                .constrainAs(enter) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        ) {
            Text(text = "Войти", color = White, style = Typography.button)
        }
    }
}