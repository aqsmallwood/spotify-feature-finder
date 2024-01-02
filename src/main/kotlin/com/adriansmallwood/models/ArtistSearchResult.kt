package com.adriansmallwood.models

import kotlinx.serialization.Serializable

@Serializable
data class ArtistSearchResult(
    val id: String,
    val name: String,
    val image: String,
)

@Serializable
data class ArtistSearchResults(
    val results: List<ArtistSearchResult>
)