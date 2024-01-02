package com.adriansmallwood.models.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumsResponse(
    val href: String,
    val items: List<AlbumResponseItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val total: Int
)

@Serializable
data class AlbumResponseItem(
//    @SerialName("album_group")
//    val albumGroup: String,
//    @SerialName("album_type")
//    val albumType: String,
    val artists: List<ArtistResponseItem>,
//    @SerialName("external_urls")
//    val externalUrls: ExternalUrls,
//    val href: String,
    val id: String,
//    val images: List<Image>,
//    @SerialName("is_playable")
//    val isPlayable: Boolean = true,
    val name: String,
//    @SerialName("release_date")
//    val releaseDate: String,
//    @SerialName("release_date_precision")
//    val releaseDatePrecision: String,
//    @SerialName("total_tracks")
//    val totalTracks: Int,
//    val type: String,
//    val uri: String
)