package com.adriansmallwood.services

import com.adriansmallwood.models.Artist
import com.adriansmallwood.models.ArtistSearchResult
import com.adriansmallwood.models.ArtistSearchResults
import com.adriansmallwood.models.spotify.ArtistSearchResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

val drake = Artist("1", "Drake")
val kendrick = Artist("2", "Kendrick Lamar")
val webbie = Artist("3", "Webbie")
val absoul = Artist("4", "Ab-Soul")
val jcole = Artist("5", "J. Cole")
val bigkrit = Artist("6", "Big K.R.I.T.")
val boosie = Artist("7", "Boosie")
val artists: List<Artist> = listOf(
    drake,
    kendrick,
    webbie,
    absoul,
    jcole,
    bigkrit,
    boosie,
)

class ArtistService(
    private val client: HttpClient,
    private val tokenService: TokenService,
) {

    suspend fun searchArtists(artistName: String): ArtistSearchResults {
        val artistSearchResponse = client.get("https://api.spotify.com/v1/search?q=$artistName&type=artist&limit=10") {
            headers {
                append("Authorization", "Bearer ${tokenService.token}")
                append("Accept", "application/json")
            }
        }.body<ArtistSearchResponse>()

        return artistSearchResponse.artists.items
            .map { ArtistSearchResult(id = it.id, name = it.name, image = it.images.lastOrNull()?.url ?: "") }
            .let { ArtistSearchResults(it) }
    }
}