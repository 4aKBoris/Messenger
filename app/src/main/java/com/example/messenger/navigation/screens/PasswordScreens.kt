package com.example.messenger.navigation.screens

import com.example.messenger.data.LoginData
import com.example.messenger.navigation.DATA
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class PasswordScreens: Screen {

    object OldPassword: Screen {

        override val name: String
            get() = "oldPassword"

        override val route: String
            get() = "$DATA/{$DATA}/$name"

        fun createRoute(data: LoginData) =
            "$DATA/${Json.encodeToString(data)}/$name"
    }

    object NewPassword: Screen {

        override val name: String
            get() = "newPassword"

        override val route: String
            get() = "$DATA/{$DATA}/$name"

        fun createRoute(data: LoginData) =
            "$DATA/${Json.encodeToString(data)}/$name"
    }

}
