package com.openclassrooms.arista.repositoryTests

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.repository.SleepRepository
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Sleep repository test class
 */
@RunWith(JUnit4::class)
class SleepRepositoryTest {

    @Mock
    private lateinit var sleepDao: SleepDtoDao

    private lateinit var sleepRepository: SleepRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sleepRepository = SleepRepository(sleepDao)
    }


    /**
     * Test the behavior between the Dao and the repository in normal use case.
     */
    @Test
    fun `when sleepDao returns sleeps, sleepRepository emit the sleeps`() = runBlocking {
        // Arrange
        val expectedSleeps = listOf(
            SleepDto(sleepid = 1, startTime = 2000L, duration = 15 , quality = 5, ownerId = 1),
            SleepDto(sleepid = 1, startTime = 2000L, duration = 15 , quality = 5, ownerId = 1)
        )

        val sleepsFlow = flow { emit(expectedSleeps) }
        Mockito.`when`(sleepDao.getAllSleep(1)).thenReturn(sleepsFlow)

        // Act
        val result = sleepRepository.getAllSleep(1)


        // Assert
        TestCase.assertNotNull(result)
        TestCase.assertEquals(sleepsFlow, result)

        //test flow value integrity
        result.collect { sleepDtos ->
            val sleeps = sleepDtos.map { it }
            TestCase.assertEquals(expectedSleeps, sleeps)
        }



    }

    /**
     * Test the behavior between the Dao and the repository with null returned value.
     */
    @Test
    fun `when sleepDao returns null, sleepRepository emits null`() = runBlocking {
        // Arrange
        Mockito.`when`(sleepDao.getAllSleep(999)).thenReturn(null )
        // Act
        val result = sleepRepository.getAllSleep(999)
        // Assert
        TestCase.assertNull(result)
    }

}