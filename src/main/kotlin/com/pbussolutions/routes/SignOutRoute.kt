package com.pbussolutions.routes

import com.pbussolutions.domain.model.ApiResponse
import com.pbussolutions.domain.model.Endpoint
import com.pbussolutions.domain.model.UserSession
import com.pbussolutions.routes.ApiConstants.AUTH_SESSION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.signOutRoute() {

    authenticate(AUTH_SESSION) {
        get(Endpoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }
    }
}