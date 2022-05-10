package com.lucasob.userapi.api

import com.lucasob.userapi.model.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    private val log = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ExceptionHandler(UserDoesNotExistException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun getResponse(e: UserDoesNotExistException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.NOT_FOUND.value(), reason = "User with id ${e.id} does not exist")
    }

    @ExceptionHandler(AccountDoesNotExistException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun getResponse(e: AccountDoesNotExistException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.NOT_FOUND.value(), reason = "User with id ${e.id} does not exist")
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun getResponse(e: UserAlreadyExistsException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.BAD_REQUEST.value(), reason = "Cannot create user")
    }

    @ExceptionHandler(UserNotEligibleException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun getResponse(e: UserNotEligibleException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.BAD_REQUEST.value(), reason = "User ${e.id} is not eligible for an account")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun getResponse(e: MethodArgumentNotValidException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.BAD_REQUEST.value(), reason = "Request contained invalid fields")
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun getResponse(e: HttpRequestMethodNotSupportedException): ApiError {
        log.error("Request resulted in error", e)
        return ApiError(code = HttpStatus.METHOD_NOT_ALLOWED.value(), reason = "Method not allowed")
    }
}