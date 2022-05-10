package com.lucasob.userapi.service

import com.lucasob.userapi.repository.UserRepository
import com.lucasob.userapi.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class ZipUserService @Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun getAllUsers() = userRepository.findAll().toList()

    @Throws(UserDoesNotExistException::class)
    override fun getUserById(userId: String): User {
        try {
            return userRepository.findById(userId).get()
        } catch (e: NoSuchElementException) {
            throw UserDoesNotExistException(id = userId)
        }
    }

    override fun createUser(user: UserCreateRequest): User {
        try {
            // at the final stage use a bidirectional type kinda deal
            return userRepository.save(user.toUser())
        } catch (e: DuplicateKeyException) {
            throw UserAlreadyExistsException()
        }
    }
}