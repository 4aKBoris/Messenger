package com.example.messenger.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.navigation.screens.PasswordScreens
import com.example.messenger.ui.screens.settings.password.newP.NewPasswordScreen
import com.example.messenger.ui.screens.settings.password.newP.NewPasswordViewModel
import com.example.messenger.ui.screens.settings.password.old.OldPasswordScreen
import com.example.messenger.ui.screens.settings.password.old.OldPasswordViewModel

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addOldPasswordScreen(
    navController: NavController,
    viewModel: OldPasswordViewModel,
) {
    composable(
        route = PasswordScreens.OldPassword.route
    ) {
        OldPasswordScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addNewPasswordScreen(
    navController: NavController,
    viewModel: NewPasswordViewModel,
) {
    composable(
        route = PasswordScreens.NewPassword.route
    ) { backStackEntry ->

        val password = backStackEntry.arguments?.getString(PASSWORD)
        requireNotNull(password) { "Password parameter wasn't found. Please make sure it's set!" }
        NewPasswordScreen(
            oldPassword = password,
            viewModel = viewModel,
            navController = navController
        )
    }
}
