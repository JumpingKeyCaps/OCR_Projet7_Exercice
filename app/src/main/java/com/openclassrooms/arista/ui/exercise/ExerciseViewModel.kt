package com.openclassrooms.arista.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.data.DefaultUserConfig.Companion.USER_ID
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *
 * The ViewModel for the exercise Section
 *
 */
@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {
    private val _exercisesFlow = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesFlow: StateFlow<List<Exercise>> = _exercisesFlow.asStateFlow()

    init {
        loadAllExercises()
    }

    /**
     * Method to delete an exercise.
     *
     * @param exercise the exercise object to delete.
     */
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteExerciseUseCase.execute(exercise)
            loadAllExercises()
        }
    }

    /**
     * Method to load all the user exercises.
     */
    private fun loadAllExercises() {
        viewModelScope.launch(Dispatchers.IO) {

            val flow = getAllExercisesUseCase.execute(USER_ID)
            flow.collect { exerciseDtos ->  // Use collect for the entire list
                val exercises = exerciseDtos.map { Exercise.fromDto(it) }
                _exercisesFlow.value = exercises
            }

        }
    }

    /**
     * Method to add a new exercises record.
     *
     * @param exercise the exercise to add.
     */
    fun addNewExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewExerciseUseCase.execute(exercise,USER_ID)
            loadAllExercises()
        }
    }
}
