package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
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
        MockitoAnnotations.initMocks(this)
        getUserUseCase = GetUserUsecase(userRepository)
    }

    @After
    fun tearDown(){
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test if the use case return the user from the repository
     */
    @Test
    fun `when repository returns user, use case should return him`() = runBlocking {
        // Arrange
        val expectedUser = User(
            name = "John",
            email = "Jdoe@test.test",
            password = "Xch7893Huuieei9388ko9"
        )
        val userFlow = flow { emit(expectedUser) } // Create a Flow directly
        `when`(userRepository.getUser()).thenReturn(userFlow) // Mock the Flow

        // Act
        val result = getUserUseCase.execute() // Use case should still return a User

        // Assert
        assertEquals(expectedUser, result) // Use assertEquals for clear comparison

    }


    /**
     * Test the use case return null  if the user returned by the repository is null.
     */
    @Test
    fun `when repository returns null , use case should return null`() = runBlocking {
        // Arrange
        Mockito.`when`(userRepository.getUser()).thenReturn(null)
        // Act
        val result = getUserUseCase.execute()
        // Assert
        TestCase.assertTrue(result == null)
    }


}