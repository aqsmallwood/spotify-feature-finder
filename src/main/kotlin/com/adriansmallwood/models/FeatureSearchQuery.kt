package com.adriansmallwood.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureSearchQuery(
    val artists: List<FeatureSearchArtist>,
)

@Serializable
data class FeatureSearchArtist(
    @SerialName("id")
    val artistSpotifyId: String,
    @SerialName("name")
    val artistName: String,
)