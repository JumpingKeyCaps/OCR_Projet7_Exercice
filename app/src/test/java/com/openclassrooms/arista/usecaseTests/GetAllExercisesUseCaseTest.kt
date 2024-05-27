package com.openclassrooms.arista.usecaseTests

import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
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
 * Test Class for the use case   GetAllExercise.
 */
@RunWith(JUnit4::class)
class GetAllExercisesUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    /**
     * Test if the use case return the exercise list from the repository
     */
    @Test
    fun `when repository returns exercises, use case should return them`() = runBlocking {
        // Arrange
        val fakeExercises = flow {emit(listOf(
            ExerciseDto(
                startTime = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli(),
                duration = 30,
                category = ExerciseCategory.Running.name,
                intensity = 5,
                ownerId = 1
            ),
            ExerciseDto(
                startTime = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli(),
                duration = 45,
                category = ExerciseCategory.Riding.name,
                intensity = 7,
                ownerId = 1
            )
        ))  }
        Mockito.`when`(exerciseRepository.getAllExercises(1)).thenReturn(fakeExercises)


        // Act
        val result = getAllExercisesUseCase.execute(1)


        // Assert
        assertEquals(fakeExercises, result)
    }

    /**
     * Test the use case return an empty list if the list returned by the repository is empty.
     */
    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(exerciseRepository.getAllExercises(999)).thenReturn(flow { emit(emptyList()) })
        // Act
        val result = getAllExercisesUseCase.execute(999)
        // Assert
        TestCase.assertTrue(result.first().isEmpty())
    }


}