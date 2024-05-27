package com.openclassrooms.arista.usecaseTests

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
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
        MockitoAnnotations.openMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }


    /**
     * Test the exercise is added in repository
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun adds_exercise_to_repository_on_execute() {
        val testExercise = Exercise(
            startTime = LocalDateTime.now(),
            duration = 20,
            category = ExerciseCategory.Football,
            intensity = 5
        )

         runBlocking {
            val testDispatcher = StandardTestDispatcher()
            Dispatchers.setMain(testDispatcher)
            addNewExerciseUseCase.execute(testExercise,1)

            // Verify  addExercise was well called on the repository with the correct argument
            verify(exerciseRepository).addExercise(testExercise,1)

        }



    }

}