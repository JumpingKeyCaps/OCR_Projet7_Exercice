package com.openclassrooms.arista.usecaseTests

import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
import java.time.ZoneId

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
        MockitoAnnotations.openMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test if the use case return sleepDto list flow from the repository
     */
    @Test
    fun `when repository returns a flow of sleepDto list , use case should return it`() = runBlocking {
        // Arrange
        val fakeSleep =  flow { emit(listOf(
            SleepDto(
                startTime = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli(),
                duration = 30,
                quality = 1,
                ownerId = 1
            ),
            SleepDto(
                startTime = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli(),
                duration = 50,
                quality = 5,
                ownerId = 1
            )
        ))}
        Mockito.`when`(sleepRepository.getAllSleep(1)).thenReturn(fakeSleep)

        // Act
        val result = getAllSleepsUseCase.execute(1)

        // Assert
        TestCase.assertEquals(fakeSleep, result)
    }

    /**
     * Test the use case return an empty list of sleep if the list returned by the repository is empty.
     */
    @Test
    fun `when repository returns empty list of sleep, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(sleepRepository.getAllSleep(999)).thenReturn(flow { emit(emptyList()) })
        // Act
        val result = getAllSleepsUseCase.execute(999)
        // Assert
        TestCase.assertTrue(result.first().isEmpty())
    }




}