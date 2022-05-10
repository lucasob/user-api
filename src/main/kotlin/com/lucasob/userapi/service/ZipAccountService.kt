package com.lucasob.userapi.service

import com.lucasob.userapi.repository.AccountRepository
import com.lucasob.userapi.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ZipAccountService @Autowired constructor(
    private val accountRepository: AccountRepository,
    private val userService: UserService
) : AccountService {

    @Throws(UserNotEligibleException::class, UserDoesNotExistException::class)
    override fun createAccount(userId: String): Account {
        val user = userService.getUserById(userId)

        if (!user.isEligibleForAccount()) {
            throw UserNotEligibleException(id = userId)
        }

        return accountRepository.save(Account.new(userId))
    }

    @Throws(UserDoesNotExistException::class)
    override fun getAccountsForUser(userId: String): List<Account> {
        userService.getUserById(userId)
        return accountRepository.findAccountByBelongsToUserId(userId)
    }

    @Throws(UserDoesNotExistException::class, AccountDoesNotExistException::class)
    override fun getAccount(userId: String, accountId: String): Account {
        // Ensure the user exists -> I'm sure there's a way better way of doing this
        userService.getUserById(userId)
        return accountRepository.findAccountById(accountId) ?: throw AccountDoesNotExistException(id = accountId)
    }
}