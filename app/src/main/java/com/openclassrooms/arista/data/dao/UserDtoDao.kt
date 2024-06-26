package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface of the User dto.
 *  regroup all CRUD actions for user table.
 */
@Dao
interface UserDtoDao {

    /**
     * Method to INSERT a new user.
     * @param user the user to insert.
     * @return represents the primary key of the inserted user row (auto-gen by Room)
     */
    @Insert
    suspend fun insertUser(user: UserDto): Long



    /**
     * Method to GET an user by ID.
     * @param id the user dto id to get.
     * @return a Flow of the user object.
     */
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id:Long): Flow<UserDto>


    /**
     * Method to DELETE a user.
     * @param user the user dto to delete.
     */
    @Delete
    suspend fun deleteUser(user: UserDto)


}