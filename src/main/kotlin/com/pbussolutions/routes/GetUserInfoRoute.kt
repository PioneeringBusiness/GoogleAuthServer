package com.pbussolutions.routes

import com.pbussolutions.domain.model.ApiResponse
import com.pbussolutions.domain.model.Endpoint
import com.pbussolutions.domain.model.UserSession
import com.pbussolutions.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInfoRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    // TODO: 4/2/22 Set auth-session as constant
    authenticate("auth-session") {
        get(Endpoint.GetUserInfo.path) {
            val userSession = call.principal<UserSession>()
            if(userSession == null) {
                app.log.info("INVALID SESSION")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            user = userDataSource.getUserInfo(userId = userSession.id)
                        ),
                        status = HttpStatusCode.OK
                    )
                } catch(e: Exception) {
                    app.log.info("GETTING USER ERROR: ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
    }
}