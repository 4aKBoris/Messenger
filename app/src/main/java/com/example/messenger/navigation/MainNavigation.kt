package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.ui.screens.main.chat.ChatScreen
import com.example.messenger.ui.screens.main.chat.ChatViewModel
import com.example.messenger.ui.screens.main.welcome.WelcomeScreen

@Composable
fun Navigation(activity: ComponentActivity) {

    val navController = rememberNavController()

    val chatViewModel by activity.viewModels<ChatViewModel>()

    NavHost(navController, startDestination = MainScreens.Welcome.route) {
        addWelcomeScreen(navController = navController)
        addRegistrationGraph(
            navController = navController,
            activity = activity
        )
        addAuthorizationGraph(
            navController = navController,
            activity = activity
        )
        addChatScreen(navController = navController, viewModel = chatViewModel)
    }
}

private fun NavGraphBuilder.addWelcomeScreen(
    navController: NavController
) {
    composable(route = MainScreens.Welcome.route) {
        WelcomeScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addChatScreen(navController: NavController, viewModel: ChatViewModel) {
    composable(
        route = MainScreens.Chat.route,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(ID)
        requireNotNull(id) { "id parameter wasn't found. Please make sure it's set!" }
        ChatScreen(
            id = id,
            viewModel = viewModel
        )
    }
}

const val PHONE_NUMBER = "phoneNumber"
const val PASSWORD = "password"
const val ID = "id"