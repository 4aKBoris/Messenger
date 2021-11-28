package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.messenger.navigation.screens.AuthorizationScreens
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.ui.screens.authorization.enter.password.EnterPasswordScreen
import com.example.messenger.ui.screens.authorization.enter.password.EnterPasswordViewModel
import com.example.messenger.ui.screens.authorization.login.LoginScreen
import com.example.messenger.ui.screens.authorization.login.LoginViewModel

fun NavGraphBuilder.addAuthorizationGraph(navController: NavController, activity: ComponentActivity) {

    val loginViewModel by activity.viewModels<LoginViewModel>()

    val enterPasswordViewModel by activity.viewModels<EnterPasswordViewModel>()

    navigation(
        route = MainScreens.Authorization.route,
        startDestination = AuthorizationScreens.Login.route
    ) {
        addLoginScreen(navController = navController, viewModel = loginViewModel)
        addEnterPasswordScreen(navController = navController, viewModel = enterPasswordViewModel)
    }
}

private fun NavGraphBuilder.addLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    composable(route = AuthorizationScreens.Login.route) {
        LoginScreen(navController = navController, viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addEnterPasswordScreen(
    navController: NavController,
    viewModel: EnterPasswordViewModel
) {
    composable(route = AuthorizationScreens.EnterPassword.route) { backStackEntry ->
        val phoneNumber = backStackEntry.arguments?.getString(PHONE_NUMBER)
        requireNotNull(phoneNumber) { "phoneNumber parameter wasn't found. Please make sure it's set!" }
        EnterPasswordScreen(
            navController = navController,
            phoneNumber = phoneNumber,
            viewModel = viewModel
        )
    }
}