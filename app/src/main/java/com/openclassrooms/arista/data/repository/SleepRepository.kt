package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for the Sleep records
 */
class SleepRepository @Inject constructor(private val sleepDao: SleepDtoDao) {

    /**
     * Method to get All sleep record of an user.
     *
     * @param userId the user id to use.
     * @return a flow list of SleepDto
     */
    fun getAllSleep(userId: Long): Flow<List<SleepDto>>{
        return sleepDao.getAllSleep(userId)
    }

    /**
     * Method to add a new sleep record for an user.
     *
     * @param sleep the sleep object to add.
     * @param userId the user id to use.
     */
    suspend fun addSleep(sleep: Sleep,userId: Long) {
        sleepDao.insertSleep(sleep.toDto(userId))
    }

}