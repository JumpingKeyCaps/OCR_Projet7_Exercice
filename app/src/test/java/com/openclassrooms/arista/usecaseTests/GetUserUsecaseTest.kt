package com.openclassrooms.arista.usecaseTests

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Test Class for the use case   GetUser.
 */
@RunWith(JUnit4::class)
class GetUserUsecaseTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var getUserUseCase: GetUserUsecase

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getUserUseCase = GetUserUsecase(userRepository)
    }

    @After
    fun tearDown(){
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test if the use case return the userDto object from the repository
     */
    @Test
    fun `when repository returns userDto, use case should return him`() = runBlocking {
        // Arrange
        val expectedUserDto = UserDto(
            id = 1,
            nom = "John",
            email = "Jdoe@test.test",
            password = "Xch7893Huuieei9388ko9"
        )
        val userFlow = flow { emit(expectedUserDto) } // Create a Flow directly
        `when`(userRepository.getUser(1)).thenReturn(userFlow) // Mock the Flow

        // Act
        val result = getUserUseCase.execute(1) // Use case should still return a UserDto

        val resultList = result.toList() // Convert Flow to List
        val emittedValue = resultList.firstOrNull()

        // Assert
        assertNotNull(emittedValue)
        assertEquals(expectedUserDto, emittedValue)

    }


    /**
     * Test the use case return null  if the user returned by the repository is null.
     */
    @Test
    fun `when repository returns null , use case should return null`() = runBlocking {
        // Arrange
        `when`(userRepository.getUser(999)).thenReturn(flow { emit(null) }) // Mock with Flow of null
        // Act
        val result = getUserUseCase.execute(999)
        // Assert
       // assertNull(result)
        TestCase.assertTrue(result.first() == null)
    }


}