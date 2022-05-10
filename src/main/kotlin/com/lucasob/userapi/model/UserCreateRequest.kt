package com.lucasob.userapi.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class UserCreateRequest(

    @get:Email
    @get:NotBlank(message = "Email can't be blank")
    val emailAddress: String,

    @get:NotBlank(message = "Name can't be blank")
    val name: String,

    @Schema(description = "The monthly salary of the user, represented in cents")
    @get:Min(value = 0, message = "Can't have negative income")
    val monthlySalary: Long,

    @Schema(description = "The monthly salary of the user, represented in cents")
    @get:Min(value = 0, message = "Can't have negative expenses")
    val monthlyExpenses: Long
)

/**
 * UserCreateRequest will create a new User
 */
fun UserCreateRequest.toUser() = User(
    id = null,
    emailAddress = this.emailAddress,
    name = this.name,
    monthlySalary = this.monthlySalary,
    monthlyExpenses = this.monthlyExpenses
)