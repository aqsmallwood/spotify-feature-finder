package com.adriansmallwood.plugins

import com.adriansmallwood.routes.artistSearchRoute
import com.adriansmallwood.routes.featureSearchRoute
import com.adriansmallwood.routes.homeRoute
import com.adriansmallwood.services.ArtistService
import com.adriansmallwood.services.FeatureService
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting(artistService: ArtistService, featureService: FeatureService) {
    routing {
        homeRoute()
        artistSearchRoute(artistService)
        featureSearchRoute(featureService)
        staticResources("static", "static")
    }
}

