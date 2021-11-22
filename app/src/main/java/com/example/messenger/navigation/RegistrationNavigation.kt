package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.navigation.screens.RegistrationScreens
import com.example.messenger.ui.screens.registration.create.password.CreatePasswordScreen
import com.example.messenger.ui.screens.registration.create.password.CreatePasswordViewModel
import com.example.messenger.ui.screens.registration.register.RegisterScreen
import com.example.messenger.ui.screens.registration.register.RegisterViewModel
import com.example.messenger.ui.screens.registration.user.info.UserInfoScreen
import com.example.messenger.ui.screens.registration.user.info.UserInfoViewModel

fun NavGraphBuilder.addRegistrationGraph(
    navController: NavController,
    activity: ComponentActivity
) {

    val registerViewModel by activity.viewModels<RegisterViewModel>()

    val createPasswordViewModel by activity.viewModels<CreatePasswordViewModel>()

    val userInfoViewModel by activity.viewModels<UserInfoViewModel>()

    navigation(
        route = MainScreens.Registration.route,
        startDestination = RegistrationScreens.Register.route
    ) {
        addRegisterScreen(navController = navController, viewModel = registerViewModel)
        addCreatePasswordScreen(navController = navController, viewModel = createPasswordViewModel)
        addUserInfoScreen(navController = navController, viewModel = userInfoViewModel, context = activity.baseContext)
    }
}

private fun NavGraphBuilder.addRegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    composable(route = RegistrationScreens.Register.route) {
        RegisterScreen(navController = navController, viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addCreatePasswordScreen(
    navController: NavController,
    viewModel: CreatePasswordViewModel
) {
    composable(route = RegistrationScreens.CreatePassword.route) { backStackEntry ->
        val phoneNumber = backStackEntry.arguments?.getString(PHONE_NUMBER)
        requireNotNull(phoneNumber) { "phoneNumber parameter wasn't found. Please make sure it's set!" }
        CreatePasswordScreen(
            navController = navController,
            phoneNumber = phoneNumber,
            viewModel = viewModel
        )
    }
}

private fun NavGraphBuilder.addUserInfoScreen(
    navController: NavController,
    viewModel: UserInfoViewModel,
    context: Context
) {
    composable(route = RegistrationScreens.UserInfo.route) { backStackEntry ->
        val phoneNumber = backStackEntry.arguments?.getString(PHONE_NUMBER)
        val password = backStackEntry.arguments?.getString(PASSWORD)
        requireNotNull(phoneNumber) { "phoneNumber parameter wasn't found. Please make sure it's set!" }
        requireNotNull(password) { "password parameter wasn't found. Please make sure it's set!" }
        UserInfoScreen(
            phoneNumber = phoneNumber,
            password = password,
            navController = navController,
            viewModel = viewModel,
            context = context
        )
    }
}