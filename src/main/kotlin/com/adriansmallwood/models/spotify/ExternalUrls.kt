package com.adriansmallwood.models.spotify

import kotlinx.serialization.Serializable

@Serializable
data class ExternalUrls(
    val spotify: String
)