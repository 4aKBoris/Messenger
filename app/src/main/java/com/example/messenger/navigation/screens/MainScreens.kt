package com.example.messenger.navigation.screens

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
            get() = name

        fun createRoute() = name
    }

    object SettingsData: MainScreens() {
        override val name: String
            get() = "settingsData"
        override val route: String
            get() = name

        fun createRoute() = name
    }

    object SettingsPassword: MainScreens() {
        override val name: String
            get() = "settingsPassword"
        override val route: String
        get() = name

        fun createRoute() = route
    }
}
