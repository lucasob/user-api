package com.lucasob.userapi.service

import com.lucasob.userapi.model.Account
import org.springframework.stereotype.Service

@Service
interface AccountService {
    /**
     * Create an account for a user.
     *
     * The user must be eligible for an account as stipulated
     * by the business requirements
     *
     * @param userId The Id of the already existing user
     * @throws UserNotEligibleException if the user does not meet the business
     * requirements for an account
     * @return The newly created account
     */
    fun createAccount(userId: String): Account

    /**
     * Return a list of accounts belonging to the specified user.
     *
     * @param userId The Id of the already existing user
     * @throws UserDoesNotExistException if the user does not exist
     * @return A list of all accounts belonging to the specified user
     */
    fun getAccountsForUser(userId: String): List<Account>

    /**
     * Returns an account with matching Id for the specified user
     *
     * See: getAccountsForUser.
     *
     * @param userId The id of the already existing user
     * @param accountId The id of the account belonging to the user
     * @throws AccountDoesNotExistException if the account with specified id does not exist
     * @throws UserDoesNotExistException if the user does not exist
     * @return The account specified owned by the given user
     */
    fun getAccount(userId: String, accountId: String): Account
}