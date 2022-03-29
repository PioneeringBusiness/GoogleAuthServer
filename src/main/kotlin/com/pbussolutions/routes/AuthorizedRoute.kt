package com.pbussolutions.routes

import com.pbussolutions.domain.model.ApiResponse
import com.pbussolutions.domain.model.Endpoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorizedRoute() {
   authenticate("auth-session") {
       get(Endpoint.Authorized.path) {
           call.respond(
               message = ApiResponse(success = true),
               status = HttpStatusCode.OK
           )
       }
   }
}