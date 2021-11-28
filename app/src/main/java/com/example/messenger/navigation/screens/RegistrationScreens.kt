package com.example.messenger.navigation.screens

import com.example.messenger.data.LoginData
import com.example.messenger.navigation.DATA
import com.example.messenger.navigation.PASSWORD
import com.example.messenger.navigation.PHONE_NUMBER
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
            get() = "$PASSWORD/{$PASSWORD}/$DATA/{$DATA}/$name"

        fun createRoute(password: String, data: LoginData) =
            "$PASSWORD/$password/$DATA/${Json.encodeToString(data)}/$name"
    }

    object CreatePassword : RegistrationScreens() {

        override val name: String
            get() = "createPassword"

        override val route: String
            get() = "$PHONE_NUMBER/{$PHONE_NUMBER}/$name"

        fun createRoute(phoneNumber: String) = "$PHONE_NUMBER/$phoneNumber/$name"
    }
}
