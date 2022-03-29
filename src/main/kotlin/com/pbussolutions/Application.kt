package com.pbussolutions

import io.ktor.server.application.*
import com.pbussolutions.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureAuth()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSession()
}
