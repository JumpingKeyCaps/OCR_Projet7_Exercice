package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.SleepDto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface of the sleep dto.
 *  regroup all CRUD actions for sleep table.
 */
@Dao
interface SleepDtoDao {

    /**
     * Method to INSERT a new sleep session.
     * @param sleep the sleep session to insert.
     * @return represents the primary key of the inserted sleep row (auto-gen by Room)
     */
    @Insert
    suspend fun insertSleep(sleep: SleepDto): Long


    /**
     * Method to GET all sleep for a specific user id.
     * @param userId the user id.
     * @return a Flow of the SleepDto List
     */
    @Query("SELECT * FROM sleep WHERE owner_id = :userId")
    fun getAllSleep(userId: Long): Flow<List<SleepDto>>

    /**
     * Method to DELETE a sleep.
     * @param id the Sleep dto id to delete.
     */
    @Query("DELETE FROM sleep WHERE id = :id")
    suspend fun deleteSleepById(id: Long)




}