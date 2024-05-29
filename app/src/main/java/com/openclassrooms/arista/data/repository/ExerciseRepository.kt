package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for the exercises data access.
 */
class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDtoDao) {

    // Get all exercises
    /**
     * Method to get all the exercises record of an user.
     * @param userId the user id.
     * @return Flow of the user exercises list.
     */
    fun getAllExercises(userId: Long): Flow<List<ExerciseDto>> {
        return exerciseDao.getAllExercises(userId)
    }

    // Add a new exercise
    /**
     * Method to add an exercise record to an user.
     * @param exercise the exercise data object to add.
     * @param userId the user id to use.
     */
    suspend fun addExercise(exercise: Exercise, userId: Long) {
        exerciseDao.insertExercise(exercise.toDto(userId))
    }

    // Delete an exercise
    /**
     * Method to delete an exercise record of an user (by the exercise id).
     * @param exercise the exercise data object to delete.
     */
    suspend fun deleteExercise(exercise: Exercise) {

        exercise.id?.let {
            exerciseDao.deleteExercise(exercise.toDto(exercise.id))
        }

    }
}