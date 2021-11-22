package com.example.messenger.network

import com.example.messenger.data.AuthorizationData
import com.example.messenger.data.User
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.request.*
import java.net.ConnectException

object Requests {

    private val client = HttpClient

    @Throws(ConnectException::class)
    suspend fun checkPhoneNumber(phoneNumber: String): Boolean {
        val directory = "checkPhoneNumber"
        println(phoneNumber)
        return client.get(client = client.client, directory = directory, pair = "phoneNumber" to phoneNumber)
    }

    @Throws(ConnectException::class)
    suspend fun registration(user: User): Boolean {
        val directory = "registration"
        return client.post(client = client.client, directory = directory, obj = user)
    }

    @Throws(ConnectException::class)
    suspend fun authorization(data: AuthorizationData): String {
        val c = client.client.config {
            install(Auth) {
                digest {
                    credentials {
                        DigestAuthCredentials(username = "jetbrains", password = "foobar")
                    }
                    realm = "RestAPI"
                }
            }
        }
        val r = c.get<String>("${client.IpAddress}/")
        println(r)
        return "Jr"
    }

    @Throws(ConnectException::class)
    suspend fun test() {
        val k = client.client.get<String>("${client.IpAddress}/")
        println(k)
            /*headers {
                //append(HttpHeaders.Authorization, "Digest username=\"jetbrains\", realm=\"RestAPI\", nonce=\"undefined\", uri=\"/authorization\", algorithm=\"MD5\", response=\"4d48f82aa88705499626613d481a0c9d\"")
                //append(HttpHeaders.CacheControl, "no-cache")
                //append("Postman-Token", "<calculated when request is sent>")
            }*/
    }
}