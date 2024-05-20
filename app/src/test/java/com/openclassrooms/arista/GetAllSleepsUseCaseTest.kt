package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
/**
 * Test Class for the use case   GetAllSleeps.
 */
@RunWith(JUnit4::class)
class GetAllSleepsUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository
    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test if the use case return sleeps from the repository
     */
    @Test
    fun `when repository returns sleeps, use case should return them`() = runBlocking {
        // Arrange
        val fakeSleep = listOf(
            Sleep(
                startTime = LocalDateTime.now(),
                duration = 30,
                quality = 1
            ),
            Sleep(
                startTime = LocalDateTime.now(),
                duration = 50,
                quality = 5
            )
        )
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(fakeSleep)

        // Act
        val result = getAllSleepsUseCase.execute()

        // Assert
        TestCase.assertEquals(fakeSleep, result)
    }

    /**
     * Test the use case return an empty list of sleep if the list returned by the repository is empty.
     */
    @Test
    fun `when repository returns empty list of sleep, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(emptyList())
        // Act
        val result = getAllSleepsUseCase.execute()
        // Assert
        TestCase.assertTrue(result.isEmpty())
    }




}