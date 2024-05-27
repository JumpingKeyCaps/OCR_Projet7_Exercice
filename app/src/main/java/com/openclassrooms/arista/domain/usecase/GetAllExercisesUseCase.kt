package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all the exercise records of an user.
 */
class GetAllExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    /**
     * Method to get all exercise records of the user.
     * @param userId  the user id.
     * @return a Flow of the list of SleepDto.
     */
    fun execute(userId: Long): Flow<List<ExerciseDto>> {
        return exerciseRepository.getAllExercises(userId)
    }
}