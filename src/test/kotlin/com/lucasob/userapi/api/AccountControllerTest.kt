package com.lucasob.userapi.api

import com.lucasob.userapi.model.User
import com.lucasob.userapi.repository.UserRepository
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.data.mongodb.database=test"]
)
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
internal class AccountControllerTest @Autowired constructor(private val userRepository: UserRepository) {

    @LocalServerPort
    var serverPort: Int = 0

    var testRestTemplate = TestRestTemplate()

    private fun accountsRootUrl(port: Int, userId: String) = "http://localhost:$port/api/users/$userId/accounts"

    @BeforeEach
    fun clearData() {
        userRepository.deleteAll()
    }

    @Test
    fun testCreateAccount() {

        // A customer who we know is eligible
        val eligibleUser = userRepository.save(User(null, "eligible@test.com", "Eligible", 500_000, 100_000))

        // An example of an ineligible customer
        val ineligibleUser = userRepository.save(User(null, "ineligible@test.com", "Ineligible", 100_000, 50_000))

        table(
            headers("User", "Expected Response"),
            row(eligibleUser, HttpStatus.CREATED),
            row(ineligibleUser, HttpStatus.BAD_REQUEST)
        ).forAll { user, expectedResponse ->

            val requestResult = testRestTemplate.exchange(
                URI(accountsRootUrl(serverPort, user.id!!)),
                HttpMethod.POST,
                HttpEntity(""),
                String::class.java
            )

            requestResult.statusCode shouldBe expectedResponse
        }
    }
}