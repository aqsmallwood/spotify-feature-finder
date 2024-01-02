package com.adriansmallwood.routes

import com.adriansmallwood.services.ArtistService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.artistSearchRoute(artistService: ArtistService) {
    get("/search/artist") {
        val artistName = call.parameters["artistName"] ?: ""
        val exclude = call.parameters.getAll("exclude") ?: listOf()

        val artistSearchResults = artistService.searchArtists(artistName)

        call.respond(artistSearchResults)
    }
}

