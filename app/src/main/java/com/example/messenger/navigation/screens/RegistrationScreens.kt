package com.example.messenger.navigation.screens

import com.example.messenger.navigation.PASSWORD
import com.example.messenger.navigation.PHONE_NUMBER

sealed class RegistrationScreens: Screen {

    object Register: RegistrationScreens() {

        override val name: String
            get() = "register"

        override val route: String
            get() = name

        fun createRoute() = route
    }

    object UserInfo : RegistrationScreens() {

        override val name: String
            get() = "userInfo"

        override val route: String
            get() = "$PHONE_NUMBER/{$PHONE_NUMBER}/$PASSWORD/{$PASSWORD}/$name"

        fun createRoute(phoneNumber: String, password: String) =
            "$PHONE_NUMBER/$phoneNumber/$PASSWORD/$password/$name"
    }

    object CreatePassword : RegistrationScreens() {

        override val name: String
            get() = "createPassword"

        override val route: String
            get() = "$PHONE_NUMBER/{$PHONE_NUMBER}/$name"

        fun createRoute(phoneNumber: String) = "$PHONE_NUMBER/$phoneNumber/$name"
    }
}
