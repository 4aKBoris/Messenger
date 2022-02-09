package com.example.messenger.navigation.screens

import com.example.messenger.navigation.PASSWORD

sealed class PasswordScreens: Screen {

    object OldPassword: Screen {

        override val name: String
            get() = "oldPassword"

        override val route: String
            get() = name

        fun createRoute() = name
    }

    object NewPassword: Screen {

        override val name: String
            get() = "newPassword"

        override val route: String
            get() = "$PASSWORD/{$PASSWORD}/$name"

        fun createRoute(password: String) =
            "$PASSWORD/$password/$name"
    }

}
