package com.pbussolutions.plugins

import com.pbussolutions.domain.model.Endpoint
import com.pbussolutions.domain.model.UserSession
import com.pbussolutions.routes.ApiConstants.AUTH_SESSION
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureAuth() {
    install(Authentication) {
//        session<UserSession>(name = "auth-session") {
        session<UserSession>(name = AUTH_SESSION) {
            validate { session ->
                session
            }
            challenge {
                call.respondRedirect(Endpoint.Unauthorized.path)
            }
        }
    }
}