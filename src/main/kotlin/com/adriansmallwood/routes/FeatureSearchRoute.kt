package com.adriansmallwood.routes

import com.adriansmallwood.models.FeatureSearchQuery
import com.adriansmallwood.models.FeatureSearchResult
import com.adriansmallwood.models.FeatureSearchResults
import com.adriansmallwood.services.FeatureService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.featureSearchRoute(featureService: FeatureService) {
    post("/search/features") {
        val (artists) = call.receive<FeatureSearchQuery>()
        if (artists.size < 2) return@post call.respond(HttpStatusCode.BadRequest)

        val features = featureService
            .getFeatures(artists)
            .map { FeatureSearchResult(it.id, it.name) }
            .let { FeatureSearchResults(it) }

        call.respond(features)
    }
}

