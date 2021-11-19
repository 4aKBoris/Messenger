@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.example.messenger.ui.screens.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, viewModel: CommonViewModel) {

    CommonScreen(
        viewModel = viewModel,
        navController = navController,
        screen = com.example.messenger.ui.screens.Screen.AuthenticationScreen.Login
    )
}
