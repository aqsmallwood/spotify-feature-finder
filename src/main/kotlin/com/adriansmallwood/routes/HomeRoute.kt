package com.adriansmallwood.routes

import com.adriansmallwood.templates.HomeTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*

fun Routing.homeRoute() {
    get {
        val homeTemplate = HomeTemplate
        call.respondHtmlTemplate(homeTemplate) { }
    }
}