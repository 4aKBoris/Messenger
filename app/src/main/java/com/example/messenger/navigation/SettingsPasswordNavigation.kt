package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.messenger.data.LoginData
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.navigation.screens.PasswordScreens
import com.example.messenger.ui.screens.settings.password.newP.NewPasswordScreen
import com.example.messenger.ui.screens.settings.password.newP.NewPasswordViewModel
import com.example.messenger.ui.screens.settings.password.old.OldPasswordScreen
import com.example.messenger.ui.screens.settings.password.old.OldPasswordViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.addSettingsPasswordGraph(
    navController: NavController,
    activity: ComponentActivity
) {

    val oldPasswordViewModel by activity.viewModels<OldPasswordViewModel>()

    val newPasswordViewModel by activity.viewModels<NewPasswordViewModel>()

    navigation(
        route = MainScreens.SettingsPassword.route,
        startDestination = PasswordScreens.OldPassword.route
    ) {
        addOldPasswordScreen(navController = navController, viewModel = oldPasswordViewModel)
        addNewPasswordScreen(navController = navController, viewModel = newPasswordViewModel)
    }
}

private fun NavGraphBuilder.addOldPasswordScreen(
    navController: NavController,
    viewModel: OldPasswordViewModel,
) {
    composable(
        route = PasswordScreens.OldPassword.route
    ) { backStackEntry ->

        val jsonString = backStackEntry.arguments?.getString(DATA)
        requireNotNull(jsonString) { "jsonString parameter wasn't found. Please make sure it's set!" }
        val data = Json.decodeFromString<LoginData>(jsonString)
        OldPasswordScreen(
            data = data,
            viewModel = viewModel,
            navController = navController
        )
    }
}

private fun NavGraphBuilder.addNewPasswordScreen(
    navController: NavController,
    viewModel: NewPasswordViewModel,
) {
    composable(
        route = PasswordScreens.NewPassword.route
    ) { backStackEntry ->

        val jsonString = backStackEntry.arguments?.getString(DATA)
        requireNotNull(jsonString) { "jsonString parameter wasn't found. Please make sure it's set!" }
        val data = Json.decodeFromString<LoginData>(jsonString)
        NewPasswordScreen(
            data = data,
            viewModel = viewModel,
            navController = navController
        )
    }
}
