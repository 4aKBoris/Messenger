package com.example.messenger.ui.screens.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.messenger.ui.screens.Screen

@Composable
fun RegisterScreen(navController: NavController, viewModel: CommonViewModel) {

    CommonScreen(
        viewModel = viewModel,
        navController = navController,
        screen = Screen.AuthenticationScreen.Register
    )
}