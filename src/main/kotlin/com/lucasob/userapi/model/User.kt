package com.lucasob.userapi.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Document("user")
data class User(
    @Schema(description = "MongoDB unique Id")
    @Id
    val id: String? = null,

    @Schema(description = "The email address of the user")
    @Indexed(unique = true)
    @get:Email
    @get:NotBlank(message = "Email can't be blank")
    val emailAddress: String,

    @Schema(description = "The name of the user")
    @get:NotBlank(message = "Name can't be blank")
    val name: String,

    @Schema(description = "The monthly salary of the user, declared in cents", example = "100000")
    @get:Min(value = 0, message = "Can't have negative income")
    val monthlySalary: Long,

    @Schema(description = "The monthly expenses of the user, declared in cents", example = "10000")
    @get:Min(value = 0, message = "Can't have negative expenses")
    val monthlyExpenses: Long
)

/**
 * User.isEligibleForAccount simply returns true iff the user's
 * leftover cash is greater than or equal to $1000
 *
 * Given that salaries and expenses are stored as Longs where the value
 * is stored as cents, $1000 = 100,000 cents
 */
fun User.isEligibleForAccount() = (this.monthlySalary - this.monthlyExpenses) >= 100_000