package com.pbussolutions.routes

import com.pbussolutions.domain.model.ApiResponse
import com.pbussolutions.domain.model.Endpoint
import com.pbussolutions.domain.model.UserSession
import com.pbussolutions.domain.model.UserUpdate
import com.pbussolutions.domain.repository.UserDataSource
import com.pbussolutions.routes.ApiConstants.AUTH_SESSION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.updateUserRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    authenticate(AUTH_SESSION) {
        put(Endpoint.UpdateUserInfo.path) {
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            if(userSession == null) {
                app.log.info("INVALID SESSION")
            } else {
                try {
                    updateUserInfo(
                        app = app,
                        userId = userSession.id,
                        userUpdate = userUpdate,
                        userDataSource = userDataSource
                    )
                } catch(e: Exception) {
                    app.log.info("UPDATE USER INFO ERROR")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
     }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.updateUserInfo(
    app: Application,
    userId: String,
    userUpdate: UserUpdate,
    userDataSource: UserDataSource
) {
    val response = userDataSource.updateUserInfo(
        userId = userId,
        firstName = userUpdate.firstName,
        lastName = userUpdate.lastName
    )
    if(response) {
        app.log.info("USER SUCCESSFULLY UPDATED")
        call.respond(
            message = ApiResponse(
                success = true,
                message = "Successfully Updated"

            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR UPDATING THE USER")
        call.respond(
            message = ApiResponse(
                success = false
            ),
            status = HttpStatusCode.BadRequest
        )
    }
}