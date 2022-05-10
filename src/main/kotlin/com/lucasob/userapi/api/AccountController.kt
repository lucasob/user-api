package com.lucasob.userapi.api

import com.lucasob.userapi.model.Account
import com.lucasob.userapi.model.ApiError
import com.lucasob.userapi.service.AccountService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}")
@Validated
class AccountController @Autowired constructor(private val accountService: AccountService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/accounts")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created a new account."),
        ApiResponse(
            responseCode = "400",
            description = "Unable to create new account.",
            content = [Content(schema = Schema(implementation = ApiError::class))]
        )
    )
    fun createAccount(@PathVariable("userId") userId: String): ResponseEntity<Account> {
        log.info("Requesting account creation for ID $userId")
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(userId))
    }

    @GetMapping("/accounts")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved accounts for given user id."),
        ApiResponse(
            responseCode = "404",
            description = "No user found for given user id.",
            content = [Content(schema = Schema(implementation = ApiError::class))]
        )
    )
    fun getAccountsForUser(@PathVariable("userId") userId: String): ResponseEntity<List<Account>> {
        log.info("Requesting account list for user ID $userId")
        return ResponseEntity.ok(accountService.getAccountsForUser(userId))
    }

    @GetMapping("/accounts/{accountId}")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully found account with required Id"),
        ApiResponse(
            responseCode = "404",
            description = "Account with specified Id does not exist",
            content = [Content(schema = Schema(implementation = ApiError::class))]
        )
    )
    fun getAccountForUser(
        @PathVariable("userId") userId: String,
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<Account> {
        log.info("Requesting account $accountId for user $userId")
        return ResponseEntity.ok(accountService.getAccount(userId, accountId))
    }
}