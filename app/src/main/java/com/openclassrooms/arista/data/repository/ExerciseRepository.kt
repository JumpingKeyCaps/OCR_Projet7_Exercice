package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 *
 * Repository for exercise data access.
 */
class ExerciseRepository(private val exerciseDao: ExerciseDtoDao) {

    // Get all exercises
    fun getAllExercises(userId: Long): Flow<List<ExerciseDto>> {
        return exerciseDao.getAllExercises(userId)
    }

    // Add a new exercise
    suspend fun addExercise(exercise: Exercise, userId: Long) {
        exerciseDao.insertExercise(exercise.toDto(userId))
    }

    // Delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {
        // If there is no id, you can raise an exception and catch it in the use case and viewmodel
        exercise.id?.let {
            exerciseDao.deleteExerciseById(
                id = exercise.id,
            )
        }
    }
}