package com.bonial.challengeapp.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    /**
     * Creates a new [HttpClient] with the specified engine and standard configuration.
     * The client is configured with:
     * - Content negotiation using JSON
     * - Default request content type set to JSON
     * - Logging for all HTTP requests and responses
     *
     * @param engine The HTTP client engine to use
     * @return A configured [HttpClient] instance
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
