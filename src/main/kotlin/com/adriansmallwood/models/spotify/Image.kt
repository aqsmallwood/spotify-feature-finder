package com.adriansmallwood.models.spotify

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val height: Int,
    val url: String,
    val width: Int
)