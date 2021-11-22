package com.example.messenger.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.messenger.navigation.screens.MainScreens
import com.example.messenger.ui.screens.WelcomeScreen

@Composable
fun Navigation(activity: ComponentActivity) {

    val navController = rememberNavController()

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
    }
}

private fun NavGraphBuilder.addWelcomeScreen(navController: NavController) {
    composable(route = MainScreens.Welcome.route) {
        WelcomeScreen(navController = navController)
    }
}

const val PHONE_NUMBER = "phoneNumber"
const val PASSWORD = "password"