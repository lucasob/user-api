package com.lucasob.userapi.service

import com.lucasob.userapi.model.User
import com.lucasob.userapi.model.UserCreateRequest
import org.springframework.stereotype.Service

@Service
interface UserService {

    /**
     * Create a new User.
     *
     * A new user will be persisted if all fields are valid
     * in accordance with the stipulated business requirements.
     *
     * @param user The requested user to create
     * @return
     *  - UserCreateResponse.Ok if the user is valid and stored,
     *  - UserCreateResponse.Error if the user was unable to
     *    stored, or some other error occurred.
     */
    fun createUser(user: UserCreateRequest): User

    /**
     * Returns a list of all Users
     *
     * @return A list of users
     */
    fun getAllUsers(): List<User>

    /**
     * Return a user with the specified id
     *
     * The user must exist
     *
     * @param userId The id of the user
     * @throws UserDoesNotExistException if the user with the specified id does not exist
     * @return The user with the matching id
     */
    fun getUserById(userId: String): User

}