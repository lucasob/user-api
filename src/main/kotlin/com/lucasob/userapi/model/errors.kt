package com.lucasob.userapi.model


data class ApiError(val code: Int, val reason: String)

class UserDoesNotExistException(override val message: String = "User does not exist", val id: String) :
    Exception(message)

class UserAlreadyExistsException(override val message: String = "User already exists") : Exception(message)

class AccountDoesNotExistException(override val message: String = "Account does not exist", val id: String) :
    Exception(message)

class UserNotEligibleException(
    override val message: String = "User is not eligible to open an account",
    val id: String
) : Exception(message)