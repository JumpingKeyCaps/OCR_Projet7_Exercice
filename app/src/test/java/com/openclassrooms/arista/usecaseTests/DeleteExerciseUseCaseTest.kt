package com.openclassrooms.arista.usecaseTests

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
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
 * Test Class for the use case  DeleteExercise.
 */
@RunWith(JUnit4::class)
class DeleteExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    /**
     * Test if the exercise is deleted from the repository
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletes_exercise_from_repository_on_execute() {
        val testExercise = Exercise(
            startTime = LocalDateTime.now(),
            duration = 20,
            category = ExerciseCategory.Football,
            intensity = 5
        )


        runBlocking {
            val testDispatcher = StandardTestDispatcher()
            Dispatchers.setMain(testDispatcher)
            deleteExerciseUseCase.execute(testExercise)


            // Verify that deleteExercise was called on the repository with the correct argument
            verify(exerciseRepository).deleteExercise(testExercise)

        }


    }



}