package com.example.messenger.navigation.screens

import com.example.messenger.data.LoginData
import com.example.messenger.data.User
import com.example.messenger.navigation.DATA
import com.example.messenger.navigation.USER
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class MainScreens : Screen {

    object Welcome : MainScreens() {

        override val name: String
            get() = "welcome"

        override val route: String
            get() = name

        fun createRoute() = route
    }

    object Authorization : MainScreens() {

        override val name: String
            get() = "authorization"

        override val route: String
            get() = name

        fun createRoute() = route
    }

    object Registration : MainScreens() {

        override val name: String
            get() = "registration"

        override val route: String
            get() = name

        fun createRoute() = route
    }

    object Chat : MainScreens() {

        override val name: String
            get() = "chat"

        override val route: String
            get() = "$DATA/{$DATA}/$name"

        fun createRoute(data: LoginData) =
            "$DATA/${Json.encodeToString(data)}/$name"
    }

    object SettingsData: MainScreens() {
        override val name: String
            get() = "settingsData"
        override val route: String
            get() = "$USER/{$USER}/$name"

        fun createRoute(user: User) =
            "$USER/${Json.encodeToString(user)}/$name"
    }

    object SettingsPassword: MainScreens() {
        override val name: String
            get() = "settingsPassword"
        override val route: String
        get() = name

        fun createRoute() = route
    }
}
