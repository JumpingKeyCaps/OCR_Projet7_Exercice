package com.openclassrooms.arista.repositoryTests

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.ExerciseCategory
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
 * Exercise repository test class
 */
@RunWith(JUnit4::class)
class ExerciseRepositoryTest {

    @Mock
    private lateinit var exerciseDao: ExerciseDtoDao

    private lateinit var exerciseRepository: ExerciseRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        exerciseRepository = ExerciseRepository(exerciseDao)
    }


    /**
     * Test the behavior between the Dao and the repository in normal use case.
     */
    @Test
    fun `when exerciseDao returns exercises, exerciseRepository emit the exercise`() = runBlocking {
        // Arrange
        val expectedExercises = listOf(
            ExerciseDto(id = 1, startTime = 2000L, duration = 15 , category = ExerciseCategory.Football.name, intensity = 3, ownerId = 1),
            ExerciseDto(id = 2, startTime = 2140L, duration = 5 , category = ExerciseCategory.Walking.name, intensity = 2, ownerId = 1),
            ExerciseDto(id = 3, startTime = 2300L, duration = 65 , category = ExerciseCategory.Riding.name, intensity = 5, ownerId = 1)
        )

        val exercisesFlow = flow { emit(expectedExercises) }
        Mockito.`when`(exerciseDao.getAllExercises(1)).thenReturn(exercisesFlow)

        // Act
        val result = exerciseRepository.getAllExercises(1)


        // Assert
        TestCase.assertNotNull(result)
        TestCase.assertEquals(exercisesFlow, result)

        //test flow value integrity
        result.collect { exercisesDtos ->
            val exrecises = exercisesDtos.map { it }
            TestCase.assertEquals(expectedExercises, exrecises)
        }



    }

    /**
     * Test the behavior between the Dao and the repository with null returned value.
     */
    @Test
    fun `when exerciseDao returns null, exerciseRepository emits null`() = runBlocking {
        // Arrange
        Mockito.`when`(exerciseDao.getAllExercises(999)).thenReturn(null )
        // Act
        val result = exerciseRepository.getAllExercises(999)
        // Assert
        TestCase.assertNull(result)
    }


}