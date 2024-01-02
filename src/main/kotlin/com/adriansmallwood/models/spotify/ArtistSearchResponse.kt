package com.adriansmallwood.models.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistSearchResponse(
    val artists: Artists
)

@Serializable
data class Artists(
    val href: String,
    val items: List<Item>,
    val limit: Int,
    val next: String,
    val offset: Int,
//    val previous: Any,
    val total: Int
)

@Serializable
data class Item(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)