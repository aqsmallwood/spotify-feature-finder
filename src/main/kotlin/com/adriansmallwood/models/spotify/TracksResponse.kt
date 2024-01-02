package com.adriansmallwood.models.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksResponse(
    val href: String,
    val items: List<TrackResponseItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)

@Serializable
data class TrackResponseItem(
    val artists: List<TrackArtist>,
//    @SerialName("available_markets")
//    val availableMarkets: List<String>,
//    @SerialName("disc_number")
//    val discNumber: Int,
//    @SerialName("duration_ms")
//    val durationMs: Int,
//    val explicit: Boolean,
//    @SerialName("external_urls")
//    val externalUrls: ExternalUrls,
//    val href: String,
    val id: String,
//    @SerialName("is_local")
//    val isLocal: Boolean,
//    @SerialName("is_playable")
//    val isPlayable: Boolean = true,
//    @SerialName("linked_from")
//    val linkedFrom: LinkedFrom,
    val name: String,
//    @SerialName("preview_url")
//    val previewUrl: String? = null,
//    val restrictions: Restrictions? = null,
//    @SerialName("track_number")
//    val trackNumber: Int,
//    val type: String,
//    val uri: String,
)

@Serializable
data class TrackArtist(
//    @SerialName("external_urls")
//    val externalUrls: ExternalUrls,
//    val href: String,
    val id: String,
    val name: String,
//    val type: String,
//    val uri: String,
)

@Serializable
data class LinkedFrom(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)

@Serializable
data class Restrictions(
    val reason: String
)