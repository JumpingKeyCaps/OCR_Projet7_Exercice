package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    fun execute(userId: Long): Flow<List<ExerciseDto>> {
        return exerciseRepository.getAllExercises(userId)
    }
}