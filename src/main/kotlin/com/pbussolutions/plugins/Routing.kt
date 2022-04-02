package com.pbussolutions.plugins

import com.pbussolutions.domain.repository.UserDataSource
import com.pbussolutions.routes.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {

    routing {
        val userDataSource: UserDataSource by inject(UserDataSource::class.java)
        rootRoute()
        tokenVerificationRoute(application, userDataSource)
        getUserInfoRoute(application, userDataSource)
        authorizedRoute()
        unauthorizedRoute()
    }
}
