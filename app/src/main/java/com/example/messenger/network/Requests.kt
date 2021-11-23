package com.example.messenger.network

import androidx.navigation.NavController
import com.example.messenger.data.User
import com.example.messenger.navigation.screens.MainScreens
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException

object Requests {

    private val client = HttpClient

    private const val PhoneNumber = "phoneNumber"

    @Throws(ConnectException::class)
    suspend fun checkPhoneNumber(phoneNumber: String): Boolean {
        val directory = "checkPhoneNumber"
        return client.get(client = client.client, directory = directory, key = PhoneNumber, obj = phoneNumber)
    }

    @Throws(ConnectException::class)
    suspend fun registration(user: User): Boolean {
        val directory = "registration"
        return client.post(client = client.client, directory = directory, obj = user)
    }

    suspend fun authorization(phoneNumber: String, password: String, navController: NavController) {

        client.client = client.client.config {
            install(Auth) {
                digest {

                    realm = "RestAPI"

                    credentials {
                        DigestAuthCredentials(username = phoneNumber, password = password)
                    }
                }
            }
        }
        val id = checkAuthorization(phoneNumber = phoneNumber)
        withContext(Dispatchers.Main) {
            navController.backQueue.clear()
            navController.navigate(MainScreens.Chat.createRoute(id))
        }
    }

    private suspend fun checkAuthorization(phoneNumber: String): Int {
        val directory = "authorization"
        return client.get(client = client.client, directory = directory, key = PhoneNumber, obj = phoneNumber)
    }
}