package com.adriansmallwood.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class TokenService(
    private val client: HttpClient,
    private val credentials: String,
) {
    private val tokenEndpoint: String = "https://accounts.spotify.com/api/token"

    lateinit var token: String

    init {
        runBlocking {
            refreshToken()
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun refreshToken(): String {
        var tokenResponse = client.post(tokenEndpoint) {
            body = FormDataContent(
                Parameters.build {
                    append("grant_type", "client_credentials")
                }
            )
            header("Content-Type", "application/x-www-form-urlencoded")
            header("Authorization", "Basic $credentials")
        }.body<TokenResponse>()

        token = tokenResponse.accessToken
        return token
    }


}

@Serializable
data class TokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Int,
)