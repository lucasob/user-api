package com.lucasob.userapi

import com.lucasob.userapi.api.UserController
import com.lucasob.userapi.repository.AccountRepository
import com.lucasob.userapi.repository.UserRepository
import com.lucasob.userapi.service.ZipUserService
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class UserApiApplicationTests {

	@MockBean
	private lateinit var mongoTemplate: MongoTemplate

	@MockBean
	private lateinit var gridFsTemplate: GridFsTemplate

	@MockBean
	private lateinit var userRepository: UserRepository

	@MockBean
	lateinit var accountRepository: AccountRepository

	@MockBean
	private lateinit var zipUserService: ZipUserService

	@Autowired
	private lateinit var controller: UserController

	@Test
	fun contextLoads() {}

	@Test
	fun controllerSmokeTest() {
		controller shouldNotBe null
	}

}
