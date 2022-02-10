package com.example.messenger.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object HttpClient {

    var client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend fun get(directory: String, value: Any? = null, key: String = ""): HttpResponse = client.get("$IpAddress/$directory") {
        if (value != null) parameter(key = key, value = value)
    }

    suspend fun post(directory: String, value: Any): HttpResponse = client.post("$IpAddress/$directory") {
        body = value
    }

    suspend fun put(directory: String, value: Any): HttpResponse = client.put("$IpAddress/$directory") {
        body = value
    }

    suspend fun delete(directory: String): HttpResponse = client.delete("$IpAddress/$directory")

    fun authorization(name: String, password: String) {
        client = client.config {
            install(Auth) {
                digest {

                    realm = "RestAPI"

                    credentials {
                        DigestAuthCredentials(username = name, password = password)
                    }
                }
            }
        }
    }

    const val IpAddress = "http://192.168.1.154:8081"
}