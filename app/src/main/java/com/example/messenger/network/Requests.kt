package com.example.messenger.network

import com.example.messenger.data.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
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

    suspend fun registration(user: UserRegistration): Boolean {
        val directory = "registration"
        return client.post(client = client.client, directory = directory, obj = user)
    }

    suspend fun authorization(data: LoginData, password: String): Boolean {

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
        return client.post(obj = data, directory = directory, client = client.client)
    }

    suspend fun getUserInfo(): FullUser {
        val directory = "user"
        return client.get(
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

    suspend fun getMessage(id: Int): List<Message> {
        val directory = "message"
        return client.get(key = "id", obj = id, client = client.client, directory = directory)
    }

    suspend fun setMessage(message: Message): Int {
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