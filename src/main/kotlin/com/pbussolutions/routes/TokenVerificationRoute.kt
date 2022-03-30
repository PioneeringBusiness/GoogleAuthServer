package com.pbussolutions.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.pbussolutions.domain.model.ApiRequest
import com.pbussolutions.domain.model.Endpoint
import com.pbussolutions.domain.model.UserSession
import com.pbussolutions.util.Constants.AUDIENCE
import com.pbussolutions.util.Constants.ISSUER
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.lang.Exception

fun Route.tokenVerificationRoute(app: Application) {

    post(Endpoint.TokenVerification.path) {
        val request = call.receive<ApiRequest>()
        if(request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(tokenId = request.tokenId)
            if(result != null) {
//                val sub = result.payload["sub"].toString()
                val name = result.payload["name"].toString()
                val emailAddress = result.payload["email"].toString()
//                val profilePhoto = result.payload["picture"].toString()
                app.log.info("TOKEN SUCCESSFULLY VERIFIED: $name, $emailAddress")

                call.sessions.set(UserSession(id = "123", name = "Charlie"))
                call.respondRedirect(Endpoint.Authorized.path)
            } else {
                app.log.info("TOKEN VERIFICATION FAILED")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }
        } else {
            app.log.info("EMPTY TOKEN ID")
            call.respondRedirect(Endpoint.Unauthorized.path)
        }

    }
}


fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? {

    try {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(AUDIENCE))
            .setIssuer(ISSUER)
            .build()
        return verifier.verify(tokenId)
    } catch (e: Exception) {
        return null
    }

}