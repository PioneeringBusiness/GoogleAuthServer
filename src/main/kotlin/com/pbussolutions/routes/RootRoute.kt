package com.pbussolutions.routes

import com.pbussolutions.domain.model.Endpoint
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.rootRoute() {

    get(Endpoint.Root.path) {
        call.respondText("Welcome to Ktor Google Auth Server!")
    }
}