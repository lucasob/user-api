package com.lucasob.userapi.model

import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class UserKtTest {

    @Test
    fun testIsEligibleForAccount() {
        table(
            headers("User", "Should be eligible"),
            row(User(null, "test@test.com", "James", 200_000, 100_000), true),
            row(User(null, "test@test.com", "James", 150_000, 100_000), false),
            row(User(null, "test@test.com", "James", 100_000, 0), true)
        ).forAll { user, expected ->
            user.isEligibleForAccount() shouldBe expected
        }
    }
}