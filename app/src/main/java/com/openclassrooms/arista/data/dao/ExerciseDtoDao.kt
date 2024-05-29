package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface of the exercise dto.
 *  regroup all CRUD actions for exercise table.
 */
@Dao
interface ExerciseDtoDao {

    /**
     * Method to INSERT a new exercise.
     * @param exercise the Exercise dto to insert.
     * @return represents the primary key of the inserted exercise row (auto-gen by Room)
     */
    @Insert
    suspend fun insertExercise(exercise: ExerciseDto): Long

    /**
     * Method to GET all exercise for an user.
     * @param ownerId the user id.
     * @return a Flow of the exercises List for an user
     */
    @Query("SELECT * FROM exercise WHERE owner_id = :ownerId")
    fun getAllExercises(ownerId: Long): Flow<List<ExerciseDto>>

    /**
     * Method to DELETE an exercise.
     * @param exercise the Exercise dto to delete.
     */
    @Delete
    suspend fun deleteExercise(exercise: ExerciseDto)


}