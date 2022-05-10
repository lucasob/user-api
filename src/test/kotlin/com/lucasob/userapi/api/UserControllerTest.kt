package com.lucasob.userapi.api

import com.lucasob.userapi.model.User
import com.lucasob.userapi.model.UserCreateRequest
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
internal class UserControllerTest @Autowired constructor(private val userRepository: UserRepository) {

    @LocalServerPort
    var serverPort: Int = 0

    var testRestTemplate = TestRestTemplate()

    private fun userRootUrl(port: Int, userId: String) = "http://localhost:$port/api/$userId"

    @BeforeEach
    fun clearData() {
        userRepository.deleteAll()
    }

    @Test
    fun testCreateUser() {

        table(
            headers("User", "Expected Status"),

            // Easy to assess, lots of cases where creation will be successful
            row(UserCreateRequest("test@test.com", "Tester", 5000, 2000), HttpStatus.CREATED),

            // Ensure to handle unique validation errors
            row(UserCreateRequest("not_an_email", "Test", 5000, 2000), HttpStatus.BAD_REQUEST),
            row(UserCreateRequest("test@test", "", 5000, 2000), HttpStatus.BAD_REQUEST),
            row(UserCreateRequest("test@test", "Test", -100, 2000), HttpStatus.BAD_REQUEST),
            row(UserCreateRequest("test@test", "Test", 1000, -100), HttpStatus.BAD_REQUEST),

            // Duplicate email should not be accepted
            row(UserCreateRequest("test@test.com", "Test", 1000, 2000), HttpStatus.BAD_REQUEST)

        ).forAll { request, expectedResponse ->

            val requestResult = testRestTemplate.exchange(
                URI(userRootUrl(serverPort, "users")),
                HttpMethod.POST,
                HttpEntity(request),
                String::class.java
            )

            requestResult.statusCode shouldBe expectedResponse

        }
    }

    @Test
    fun testGetUser() {

        // Ensure to create a user that exists
        val user = userRepository.save(User(null, "eligible@test.com", "Eligible", 5000, 1000))

        table(
            headers("User", "Expected Response"),
            row(user, HttpStatus.OK),
            row(
                User(
                    id = "clearly-fake-and-not-inserted",
                    emailAddress = "test@test.com",
                    name = "Test",
                    monthlyExpenses = 5000,
                    monthlySalary = 1000
                ), HttpStatus.NOT_FOUND
            )
        ).forAll { queriedUser, expectedResponse ->

            val requestResult = testRestTemplate.exchange(
                URI(userRootUrl(serverPort, "users/${queriedUser.id}")),
                HttpMethod.GET,
                HttpEntity(""),
                String::class.java
            )

            requestResult.statusCode shouldBe expectedResponse
        }
    }

}