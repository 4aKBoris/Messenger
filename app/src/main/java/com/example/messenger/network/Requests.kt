package com.example.messenger.network

import com.example.messenger.data.*
import io.ktor.client.call.*
import io.ktor.client.statement.*

object Requests {

    private val client = HttpClient

    private const val PhoneNumber = "phoneNumber"

    suspend fun checkPhoneNumber(phoneNumber: String): HttpResponse {
        val directory = "checkPhoneNumber"
        return client.get(directory = directory, key = PhoneNumber, value = phoneNumber)
    }

    suspend fun registration(user: UserRegistration): HttpResponse {
        val directory = "registration"
        return client.post(directory = directory, value = user)
    }

    fun authorization(name: String, password: String) {
        client.authorization(name = name, password = password)
    }

    suspend fun getUserInfo(): FullUser {
        val directory = "user"
        val response = client.get(directory = directory)
        return response.receive()
    }

    /*suspend fun getIcon(id: Int): ByteArray? {
        val directory = "icon"
        val icon = client.get<ByteArray>(
            key = "data",
            obj = Json.encodeToString(data),
            client = client.client,
            directory = directory
        )
        return if (icon.isEmpty()) null else icon
    }*/

    suspend fun getUsers(): List<User> {
        val directory = "chatUsers"
        val response = client.get(directory = directory)
        return response.receive()
    }

    suspend fun getMessages(): List<Message> {
        val directory = "messages"
        val response = client.get(directory = directory)
        return response.receive()
    }

    suspend fun getMessage(id: Int): List<Message> {
        val directory = "message"
        val response = client.get(key = "id", value = id, directory = directory)
        return response.receive()
    }

    suspend fun exit(): Boolean {
        val directory = "exit"
        val response = client.get(directory = directory)
        return response.receive()
    }

    suspend fun check(): Boolean {
        val directory = "check"
        val response = client.get(directory = directory)
        return response.receive()
    }

    suspend fun setMessage(message: MyMessage) {
        val directory = "message"
        client.post(directory = directory, value = message)
    }

    suspend fun deleteUser(): Boolean {
        val directory = "user"
        val response = client.delete(directory = directory)
        return response.receive()
    }

    suspend fun updatePassword(password: Password): Boolean {
        val directory = "password"
        val response = client.put(directory = directory, value = password)
        return response.receive()
    }

    suspend fun checkPassword(password: String): Boolean {
        val directory = "password"
        val response = client.get(directory = directory, key = "password", value = password)
        return response.receive()
    }

    suspend fun updateName(name: Name): Boolean {
        val directory = "user"
        val response = client.put(directory = directory, value = name)
        return response.receive()
    }

    suspend fun updateIcon(Icon: Icon): Boolean {
        val directory = "icon"
        val response = client.put(directory = directory, value = Icon)
        return response.receive()
    }

    suspend fun getRegistrationData(id: Int): String {
        val directory = "user/data"
        val response = client.get(directory = directory, value = id, key = "id")
        return response.readText()
    }
}