package com.adriansmallwood.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
)
