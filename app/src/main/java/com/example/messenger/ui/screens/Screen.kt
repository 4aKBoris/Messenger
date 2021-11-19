package com.example.messenger.ui.screens

import androidx.navigation.NavController

sealed class Screen(val route: String) {

    sealed class AuthenticationScreen(route: String, val check: Boolean, val buttonText: String): Screen(route = route) {

        open fun error(navController: NavController, phoneNumber: String) {}

        open fun nextScreen(navController: NavController, phoneNumber: String) {}

        open val errorMessage = ""

        object Login : AuthenticationScreen(route = "login", check = false, buttonText = "Зарегистрироваться") {

            override val errorMessage: String
                get() = "Аккаунт на данный телефонный номер не зарегистрирован!"

            override fun error(navController: NavController, phoneNumber: String) {
                navController.navigate(UserInfo.createRoute(phoneNumber = phoneNumber))
            }

            override fun nextScreen(navController: NavController, phoneNumber: String) {
                navController.navigate(EnterPassword.createRoute(phoneNumber = phoneNumber))
            }
        }

        object Register : AuthenticationScreen(route = "register", check = true, "Войти") {

            override val errorMessage: String
                get() = "Аккаунт на данный телефонный номер уже зарегистрирован!"

            override fun error(navController: NavController, phoneNumber: String) {
                navController.navigate(EnterPassword.createRoute(phoneNumber = phoneNumber))
            }

            override fun nextScreen(navController: NavController, phoneNumber: String) {
                navController.navigate(UserInfo.createRoute(phoneNumber = phoneNumber))
            }
        }
    }

    object Welcome: Screen(route = "welcome")

    object UserInfo: Screen(route = "phoneNumber/{phoneNumber}/userInfo") {
        fun createRoute(phoneNumber: String) = "phoneNumber/$phoneNumber/userInfo"
    }

    object EnterPassword: Screen(route = "phoneNumber/{phoneNumber}/enterPassword") {
        fun createRoute(phoneNumber: String) = "phoneNumber/$phoneNumber/enterPassword"
    }
}
