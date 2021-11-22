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

    val client = HttpClient(Android) {
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
        IP: String = IpAddress
    ): T {
        return client.post("$IP/$directory") {
            body = obj
        }
    }

    suspend inline fun <reified T> get(
        pair: Pair<String, Any>,
        client: HttpClient,
        directory: String,
        IP: String = IpAddress
    ): T {
        return client.get("$IP/$directory") {
            parameter(pair.first, pair.second)
        }
    }

    suspend inline fun <reified T> get(
        client: HttpClient,
        directory: String,
        headersBuilder: HeadersBuilder,
        IP: String = IpAddress
    ): T {
        return client.get("$IP/$directory") {
            headers {
                headersBuilder.build()
            }
        }
    }
}