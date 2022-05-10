package com.lucasob.userapi.model

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Document("account")
data class Account(

    @Schema(description = "MongoDB unique Id")
    @Id
    val id: String?,

    @Schema(description = "A mapping to the MongoDB Id of the user who owns the account")
    @get:NotEmpty(message = "An account must belong to a user")
    val belongsToUserId: String,

    @Schema(description = "The date/time that the account was opened")
    @get:NotNull(message = "An account must have an opening date")
    val accountOpenedDate: Instant,

    @Schema(description = "The balance of the account, represented in cents")
    val balance: Long,

    @Schema(description = "The closing date of the account, if it was closed")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val accountClosedDate: Date? = null
) {
    companion object {

        /**
         * Account.new is a convenience method that
         * returns a new Account for the specified user.
         *
         * The new account returned is only a JVM Data class
         * and there is no guarantee that this user exists.
         *
         * Ensure only to use where the userId has been
         * correctly checked for existence.
         */
        fun new(belongsToUserId: String) =
            Account(
                id = null,
                belongsToUserId = belongsToUserId,
                accountOpenedDate = Instant.now(),
                balance = 0L
            )
    }
}