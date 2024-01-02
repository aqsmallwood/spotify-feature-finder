package com.adriansmallwood.models

import kotlinx.serialization.Serializable

@Serializable
data class AlbumArtistResult(
    val id: String,
    val name: String,
)

@Serializable
data class AlbumArtistResults(
    val results: List<AlbumArtistResult>
)