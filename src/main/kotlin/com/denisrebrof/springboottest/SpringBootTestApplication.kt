package com.denisrebrof.springboottest

import com.denisrebrof.springboottest.user.IUserRepository
import com.denisrebrof.springboottest.user.model.User
import com.denisrebrof.springboottest.user.model.UserRole
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class SpringBootTestApplication(
    private val usersRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val user = createTestUser("dr")
        arrayOf(user).forEach(usersRepository::save)
    }

    private fun createTestUser(username: String) = User(
        username = username,
        password = passwordEncoder.encode("$username-password"),
        role = UserRole.Admin
    )
}

fun main(args: Array<String>) {
    runApplication<SpringBootTestApplication>(*args)
}