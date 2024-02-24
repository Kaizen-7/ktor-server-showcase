package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h3
import kotlinx.html.img
import kotlinx.html.title
import kotlinx.serialization.Serializable

fun Application.configureRouting() {

    routing {

        // region GET RESPONDING TEXT SAMPLE
        get("/") {
            call.respondText("Hello World!")
        }

        get("/users/{username}") {
            val user = call.parameters["username"]
            call.respondText {
                "Greetings, $user!"
            }
        }
        get("/user") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText {
                "Hi my name is $name and I'm $age years old."
            }
        }
        // endregion

        // region JSON RESPONSE SAMPLE
        get("/person") {
            try {
                val person = Person(name = "Luca", age = 26)
                call.respond(message = person, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        // endregion

        // region REDIRECT SAMPLE
        get("/redirect") {
            call.respondRedirect(url = "/moved", permanent = false)
        }

        get("/moved") {
            call.respondText {
                "Successfully redirected!"
            }
        }
        // endregion

        // region STATIC CONTENT
        staticResources(remotePath = "/assets", basePackage = "static")
        // endregion

        // region STATIC CONTENT - TEMPLATING HTML
        get("/welcome") {
            val name  = call.request.queryParameters["name"]

            call.respondHtml {
                title = "Hello"
                body {
                    h3 {
                        + "Welcome $name"
                    }
                    img (src = "logo.png")
                }
            }
        }
        // endregion
    }
}

@Serializable
data class Person(
    val name: String,
    val age: Int
)
