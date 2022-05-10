package com.lucasob.userapi.api

import com.lucasob.userapi.model.ApiError
import com.lucasob.userapi.model.User
import com.lucasob.userapi.model.UserCreateRequest
import com.lucasob.userapi.service.ZipUserService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
@Validated
class UserController(private val userService: ZipUserService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Successfully listed all users.")
    fun allUsers(): ResponseEntity<List<User>> {

        log.info("Requesting all users")

        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Located user with given id."),
        ApiResponse(
            responseCode = "404",
            description = "No user exists with given id.",
            content = [Content(schema = Schema(implementation = ApiError::class))]
        )
    )
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<User> {

        log.info("Requesting user with ID $id")
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PostMapping
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created new user."),
        ApiResponse(
            responseCode = "400",
            description = "Unable to create new user.",
            content = [Content(schema = Schema(implementation = ApiError::class))]
        )
    )
    fun createUser(@Valid @RequestBody request: UserCreateRequest): ResponseEntity<User> {

        log.info("Requesting user creation. Request: $request")
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request))
    }
}