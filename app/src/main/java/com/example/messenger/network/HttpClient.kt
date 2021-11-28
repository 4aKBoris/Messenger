package com.example.messenger.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
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

    const val IpAddress = "http://192.168.1.154:8081"

    suspend inline fun <reified T> post(
        obj: Any,
        client: HttpClient,
        directory: String,
    ): T {
        return client.post("$IpAddress/$directory") {
            body = obj
        }
    }

    suspend inline fun <reified T> get(
        key: String,
        obj: Any,
        client: HttpClient,
        directory: String,
    ): T {
        return client.get("$IpAddress/$directory") {
            parameter(key, obj)
        }
    }

    suspend inline fun <reified T> get(
        client: HttpClient,
        directory: String
    ): T {
        return client.get("$IpAddress/$directory")
    }

    suspend inline fun <reified T> delete(
        client: HttpClient,
        obj: Any,
        directory: String
    ): T {
        return client.delete("$IpAddress/$directory") {
            body = obj
        }
    }

    suspend inline fun <reified T> put(
        client: HttpClient,
        obj: Any,
        directory: String
    ): T {
        return client.put("$IpAddress/$directory") {
            body = obj
        }
    }
}