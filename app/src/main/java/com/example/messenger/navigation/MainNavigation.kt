package com.example.messenger.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.ui.screens.main.chat.ChatScreen
import com.example.messenger.ui.screens.main.chat.ChatViewModel
import com.example.messenger.ui.screens.main.welcome.WelcomeScreen
import com.example.messenger.ui.screens.settings.data.DataSettingsScreen
import com.example.messenger.ui.screens.settings.data.DataSettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(activity: ComponentActivity) {

    val navController = rememberNavController()

    val chatViewModel by activity.viewModels<ChatViewModel>()

    val dataSettingsViewModel by activity.viewModels<DataSettingsViewModel>()

    NavHost(navController, startDestination = MainScreens.Welcome.route) {
        addWelcomeScreen(navController = navController)
        addAuthorizationGraph(
            navController = navController,
            activity = activity
        )
        addRegistrationGraph(
            navController = navController,
            activity = activity
        )
        addChatScreen(navController = navController, viewModel = chatViewModel)
        addSettingsDataScreen(
            navController = navController,
            viewModel = dataSettingsViewModel,
            context = activity.applicationContext
        )
        addSettingsPasswordGraph(navController = navController, activity = activity)
    }
}

private fun NavGraphBuilder.addWelcomeScreen(
    navController: NavController
) {
    composable(route = MainScreens.Welcome.route) {
        WelcomeScreen(navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addSettingsDataScreen(
    context: Context,
    navController: NavController,
    viewModel: DataSettingsViewModel
) {
    composable(
        route = MainScreens.SettingsData.route
    ) {
        DataSettingsScreen(
            context = context,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addChatScreen(navController: NavController, viewModel: ChatViewModel) {
    composable(route = MainScreens.Chat.route) {
        ChatScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}

const val PHONE_NUMBER = "phoneNumber"
const val PASSWORD = "password"
const val DATA = "data"