package com.adriansmallwood.services

import com.adriansmallwood.models.spotify.AlbumsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.headers

class AlbumService(
    private val client: HttpClient,
    private val tokenService: TokenService,
) {
    suspend fun getAlbums(artistId: String, lastSyncedAlbumId: String?): List<String> {
        val albumIds = mutableListOf<String>()
        var nextEndpoint: String? = "https://api.spotify.com/v1/artists/$artistId/albums?include_groups=album%2Csingle%2Cappears_on&limit=50"
        while(nextEndpoint != null) {
            val albumsResponse = client.get(nextEndpoint) {
                headers {
                    append("Authorization", "Bearer ${tokenService.token}")
                    append("Accept", "application/json")
                }
            }.body<AlbumsResponse>()
            val lastSyncedAlbumIndex = lastSyncedAlbumId?.let { albumsResponse.items.indexOfFirst { it.id == lastSyncedAlbumId } }
            val lastCall = lastSyncedAlbumIndex != null && lastSyncedAlbumIndex > -1
            val currentAlbumIds = if (lastCall) {
                albumsResponse.items.subList(0, lastSyncedAlbumIndex!!)
            } else {
                albumsResponse.items
            }.map { it.id }
            albumIds.addAll(currentAlbumIds)

            if (lastCall) break

            nextEndpoint = albumsResponse.next
        }
        return albumIds
    }
}
