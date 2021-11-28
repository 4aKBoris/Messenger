package com.example.messenger.network

import androidx.navigation.NavController
import com.example.messenger.data.*
import com.example.messenger.exception.MessengerException
import com.example.messenger.navigation.screens.MainScreens
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Requests {

    private val client = HttpClient

    private const val PhoneNumber = "phoneNumber"

    suspend fun checkPhoneNumber(phoneNumber: String): Boolean {
        val directory = "checkPhoneNumber"
        return client.get(
            client = client.client,
            directory = directory,
            key = PhoneNumber,
            obj = phoneNumber
        )
    }

    suspend fun registration(user: User): Boolean {
        val directory = "registration"
        return client.post(client = client.client, directory = directory, obj = user)
    }

    suspend fun authorization(data: LoginData, password: String, navController: NavController) {

        val directory = "authorization"

        client.client = client.client.config {
            install(Auth) {
                digest {

                    realm = "RestAPI"

                    credentials {
                        DigestAuthCredentials(username = data.phoneNumber, password = password)
                    }
                }
            }
        }
        val flag = client.get<Boolean>(client = client.client, directory = directory)
        if (!flag) throw MessengerException("Не удалось авторизоваться! Попробуйте ещё раз")
        else
            withContext(Dispatchers.Main) {
                navController.backQueue.clear()
                navController.navigate(MainScreens.Chat.createRoute(data = data))
            }
    }

    suspend fun getUserInfo(data: LoginData): FullUser {
        val directory = "user"
        return client.get(
            key = "data",
            obj = Json.encodeToString(data),
            client = client.client,
            directory = directory
        )
    }

    suspend fun getIcon(data: LoginData): ByteArray? {
        val directory = "icon"
        val icon = client.get<ByteArray>(key = "data", obj = Json.encodeToString(data), client = client.client, directory = directory)
        return if (icon.isEmpty()) null else icon
    }

    suspend fun getUsers(): List<ChatUser> {
        val directory = "chatUsers"
        return client.get(client = client.client, directory = directory)
    }

    suspend fun getMessages(): List<Message> {
        val directory = "messages"
        return client.get(client = client.client, directory = directory)
    }

    suspend fun getMessage(): List<Message> {
        val directory = "message"
        return client.get(client = client.client, directory = directory)
    }

    suspend fun setMessage(message: Message): Boolean {
        val directory = "message"
        return client.post(client = client.client, directory = directory, obj = message)
    }

    suspend fun deleteUser(data: LoginData): Boolean {
        val directory = "user"
        return client.delete(client = client.client, directory = directory, obj = data)
    }

    suspend fun changePassword(changePassword: ChangePassword): Boolean {
        val directory = "user/password"
        return client.put(client = client.client, directory = directory, obj = changePassword)
    }

    suspend fun changeData(data: User): Boolean {
        val directory = "user/info"
        return client.put(client = client.client, directory = directory, obj = data)
    }

    suspend fun getUsersInfo(): List<UserInfo> {
        val directory = "user/info"
        return client.get(client = client.client, directory = directory)
    }
}