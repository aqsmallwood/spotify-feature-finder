package com.adriansmallwood.models.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ArtistsResponse(
    val artists: List<ArtistResponseItem>,
)

@Serializable
data class ArtistResponseItem(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)