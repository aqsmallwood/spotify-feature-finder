package com.adriansmallwood.templates

import io.ktor.server.html.*
import kotlinx.html.*

object HomeTemplate: Template<HTML> {
    override fun HTML.apply() {
        this.attributes["lang"] = "en"
        head {
            meta {
                charset = "UTF-8"
            }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1.0"
            }
            title {
                +"Spotify Feature Finder"
            }
            script {
                src = "/static/js/htmx.min.js"
                type = "text/javascript"
            }
        }
        body {
            div {
                id="artists-form-container"
                form {
                    action = "/artist-search"
                    method = FormMethod.get
                    attributes["hx-get"] = "/artist-search"
                    attributes["hx-target"] = "#results"
                    attributes["hx-swap"] = "outerHTML"

                    div {
                        label {
                            htmlFor = "artist"
                            +"Artist Name"
                        }
                        input {
                            type = InputType.text
                            name = "artist"
                            placeholder = "Kendrick Lamar"
                        }
                    }
                    div {
                        input {
                            type = InputType.submit
                            value = "Search Artists"
                        }
                    }
                }
                div {
                    id = "results"
                }
            }
            div {
                id="features-form-container"
                form {
                    action = "/features-search"
                    method = FormMethod.post
                    attributes["hx-post"] = "/features-search"

                    div {
                        input {
                            type = InputType.submit
                            value = "Search Features"
                        }
                    }
                }
            }
        }
    }
}
