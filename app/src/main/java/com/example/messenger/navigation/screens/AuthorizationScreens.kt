package com.example.messenger.navigation.screens

import com.example.messenger.navigation.PHONE_NUMBER

sealed class AuthorizationScreens: Screen {

    object Login : AuthorizationScreens() {

        override val name: String
            get() = "login"

        override val route: String
            get() = name

        fun createRoute() = route
    }

    object EnterPassword : AuthorizationScreens() {

        override val name: String
            get() = "enterPassword"

        override val route: String
            get() = "$PHONE_NUMBER/{$PHONE_NUMBER}/$name"

        fun createRoute(phoneNumber: String) = "$PHONE_NUMBER/$phoneNumber/$name"
    }
}

