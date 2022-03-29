package com.pbussolutions.plugins

import com.pbussolutions.routes.authorizedRoute
import com.pbussolutions.routes.rootRoute
import com.pbussolutions.routes.tokenVerificationRoute
import com.pbussolutions.routes.unauthorizedRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
        rootRoute()
        tokenVerificationRoute()
        authorizedRoute()
        unauthorizedRoute()
    }
}
