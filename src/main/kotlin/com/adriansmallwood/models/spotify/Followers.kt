package com.adriansmallwood.models.spotify

import kotlinx.serialization.Serializable

@Serializable
data class Followers(
//    val href: Any,
    val total: Int
)