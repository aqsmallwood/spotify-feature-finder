package com.adriansmallwood.services

import com.adriansmallwood.models.spotify.TrackResponseItem
import com.adriansmallwood.models.spotify.TracksResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.headers

class TrackService(
    private val client: io.ktor.client.HttpClient,
    private val tokenService: TokenService,
) {
    suspend fun getFeaturesForAlbum(albumId: String, artistId: String): List<TrackResponseItem> {
        val features = mutableListOf<TrackResponseItem>()
        var nextEndpoint: String? = "https://api.spotify.com/v1/albums/$albumId/tracks?limit=50"
        while(nextEndpoint != null) {
            val tracksResponse = client.get(nextEndpoint) {
                headers {
                    append("Authorization", "Bearer ${tokenService.token}")
                    append("Accept", "application/json")
                }
            }.body<TracksResponse>()
            val currentFeatures = tracksResponse.items.filter { item ->  item.artists.any { it.id == artistId } && item.artists.size > 1 }
            features.addAll(currentFeatures)
            nextEndpoint = tracksResponse.next
        }
        return features
    }

}
