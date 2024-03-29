package com.zendesk.mock.zendesk

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@SpringBootApplication
class ZendeskApplication

fun main(args: Array<String>) {
    runApplication<ZendeskApplication>(*args)
}

@RestController
@RequestMapping("/api/v2")
class ZendeskApiRestController {
    @GetMapping("/ticket_fields.json")
    fun getCustomFields() = mapOf("ticket_fields" to emptyList<String>())

    @GetMapping("/custom_roles.json")
    fun getCustomRoles() = mapOf("custom_roles" to emptyList<String>())

    @GetMapping("/users/{id}.json")
    fun getUser(@PathVariable id: String) = mapOf("user" to User(Random.nextInt(), Random.nextInt().toString()))

    @GetMapping("/search.json")
    fun getSearchResults(@RequestParam(required = false) param: String?, @RequestParam(required = false) query: String?) = mapOf("results" to emptyList<String>())

    @GetMapping("/tickets/{id}.json")
    fun getTicket(@PathVariable id: Int) = mapOf("ticket" to Ticket(id))

    @GetMapping("/tickets/show_many.json")
    fun getTicket(@RequestParam(required = false) ids: List<String>?) = mapOf("tickets" to emptyList<String>())

    @PutMapping("/tickets/{id}.json")
    fun updateTicket(@PathVariable id: String) = mapOf("ticket" to Ticket())

    @PostMapping("/tickets.json")
    fun createTicket(@RequestBody `object`: Any?) = mapOf("ticket" to Ticket())

    data class User(val id: Int, val name: String)
    data class Ticket(val id: Int = Math.abs(Random.nextInt()), @JsonProperty("requester_id") val requesterId: Int = Math.abs(Random.nextInt()))
}

@RestController
class HealthController {
    @GetMapping("/health")
    fun healthCheck() = "I'm still here!"
}

