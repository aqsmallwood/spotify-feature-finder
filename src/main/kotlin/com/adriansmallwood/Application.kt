package com.adriansmallwood

import com.adriansmallwood.plugins.*
import com.adriansmallwood.services.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


@Serializable
data class SpotifyTokenRequestOptions(
    @SerialName("grant_type")
    val grantType: String = "client_credentials"
)

fun Application.module() {
    val clientId = System.getenv("SPOTIFY_CLIENT_ID")
    val clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET")
    val credentials = "$clientId:$clientSecret".toByteArray().encodeBase64()
    val json = Json {
        ignoreUnknownKeys = true
    }
    val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(json)
        }
    }
    val tokenService = TokenService(client, credentials)
    val artistService = ArtistService(client, tokenService)
    val albumService = AlbumService(client, tokenService)
    val trackService = TrackService(client, tokenService)
    val featureService = FeatureService(client, albumService, trackService, tokenService)

    configureSecurity()
    configureSerialization()
    configureTemplating()
    configureDatabases()
    configureSockets()
    configureRouting(artistService, featureService)
}
