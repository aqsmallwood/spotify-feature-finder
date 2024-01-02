package com.adriansmallwood.plugins

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlin.collections.set

fun Application.configureSecurity() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Accept)
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    authentication {
        oauth("spotify-auth") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "spotify",
                    authorizeUrl = "https://accounts.spotify.com/authorize",
                    accessTokenUrl = "https://accounts.spotify.com/api/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("SPOTIFY_CLIENT_ID"),
                    clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET"),
                    defaultScopes = listOf("user-read-private", "user-read-email"),
                    extraAuthParameters = listOf("state" to generateNonce()),
                )
            }
            client = HttpClient(Apache)
        }
    }
}
