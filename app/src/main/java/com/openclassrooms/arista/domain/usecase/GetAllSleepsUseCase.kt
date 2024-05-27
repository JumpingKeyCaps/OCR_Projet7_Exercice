package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all the sleep records of an user.
 */
class GetAllSleepsUseCase @Inject constructor(private val sleepRepository: SleepRepository) {

    /**
     * Method to get all sleep record of the user.
     * @param userId the user id
     * @return a Flow of the list of SleepDto
     */
    fun execute(userId: Long): Flow<List<SleepDto>> {
        return sleepRepository.getAllSleep(userId)
    }
}