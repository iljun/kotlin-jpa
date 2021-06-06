package me.iljun.kotlinboot.user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
    val userRepository: UserRepository) {

    val login = "saveuser"
    val firstname = "ethan"
    val lastname = "won"
    val description = "first jpa with kotlin"

    fun saveUser() : User {
        val user = User(login, firstname, lastname, description)
        userRepository.save(user);
        return user
    }

    @Test
    fun `save User entity`() {
        val user = saveUser()

        Assertions.assertNotNull(user.id)
        Assertions.assertEquals(user.login, login)
        Assertions.assertEquals(user.firstname, firstname)
        Assertions.assertEquals(user.lastname, lastname)
        Assertions.assertEquals(user.description, description)
    }

    @Test
    fun `update User entity`() {
        val user = saveUser()
        val description = "update field"
        val userId = user.id
        user.description = description
        userRepository.save(user)

        Assertions.assertEquals(user.id, userId)
        Assertions.assertEquals(user.login, login)
        Assertions.assertEquals(user.firstname, firstname)
        Assertions.assertEquals(user.lastname, lastname)
        Assertions.assertEquals(user.description, description)
    }

    @Test
    fun `find User entity`() {
        val user = saveUser()
        val userId = user.id
        userId?.let {
            val selectUser = userRepository.findById(it).get()
            Assertions.assertEquals(user.id, selectUser.id)
        }
    }

}
