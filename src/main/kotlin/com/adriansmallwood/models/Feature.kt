package com.adriansmallwood.models

import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val id: String,
    val name: String,
)
