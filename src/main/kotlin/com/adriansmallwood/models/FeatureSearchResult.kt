package com.adriansmallwood.models

import kotlinx.serialization.Serializable

@Serializable
data class FeatureSearchResult(val id: String, val name: String)

@Serializable
data class FeatureSearchResults(val results: List<FeatureSearchResult>)
