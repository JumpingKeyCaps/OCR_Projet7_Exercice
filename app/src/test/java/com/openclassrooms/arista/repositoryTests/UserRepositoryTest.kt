package com.openclassrooms.arista.repositoryTests

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * User repository test class
 */
@RunWith(JUnit4::class)
class UserRepositoryTest {
    @Mock
    private lateinit var userDao: UserDtoDao

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(userDao)
    }

    /**
     * Test the behavior between the Dao and the repository in normal use case.
     */
    @Test
    fun `when userDao returns user, userRepository emits the user`() = runBlocking {
        // Arrange
        val expectedUser = UserDto(id = 1, nom = "John", email = "Jdoe@test.test", password = "xhuiehfuiwef7878")
        val userFlow = flow { emit(expectedUser) }
        `when`(userDao.getUserById(1)).thenReturn(userFlow)

        // Act
        val result = userRepository.getUser(1)

        // Assert
        assertNotNull(result)
        assertEquals(userFlow, result)

        //test flow value integrity
        result.collect { userDtos ->
            assertEquals(expectedUser, userDtos)
        }

    }

    /**
     * Test the behavior between the Dao and the repository with null returned value.
     */
    @Test
    fun `when userDao returns null, userRepository emits null`() = runBlocking {
        // Arrange
        `when`(userDao.getUserById(999)).thenReturn(null )
        // Act
        val result = userRepository.getUser(999)
        // Assert
        assertNull(result)
    }


}