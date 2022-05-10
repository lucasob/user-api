package com.lucasob.userapi.repository

import com.lucasob.userapi.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<Account, String> {

    fun findAccountByBelongsToUserId(userId: String): List<Account>

    fun findAccountById(accountId: String): Account?

}