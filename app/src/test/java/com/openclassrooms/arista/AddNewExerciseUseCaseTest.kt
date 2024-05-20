package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import java.time.LocalDateTime

/**
 * Test Class for the use case   AddNewExercise.
 */
@RunWith(JUnit4::class)
class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test the exercise is added in repository
     */
    @Test
    fun `adds_exercise_to_repository_on_execute`() {
        val testExercise = Exercise(
            startTime = LocalDateTime.now(),
            duration = 20,
            category = ExerciseCategory.Football,
            intensity = 5
        )

         runBlocking {
            val testDispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(testDispatcher)
            addNewExerciseUseCase.execute(testExercise)

            // Verify  addExercise was well called on the repository with the correct argument
            verify(exerciseRepository).addExercise(testExercise)

        }



    }

}