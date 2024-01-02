package com.adriansmallwood.templates

import com.adriansmallwood.models.Artist
import io.ktor.server.html.*
import kotlinx.html.*

class ArtistSearchResultsTemplate(private val filteredArtists: List<Artist>): Template<HTML> {
    override fun HTML.apply() {
        body {
            div {
                id = "results"
                filteredArtists.forEach { artist ->
                    div {
                        div {
                            +artist.name
                        }
                        div {
                            button {
                                type = ButtonType.button
                                +"Add"
                            }
                        }
                    }
                }
            }
        }
    }

}
