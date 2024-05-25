package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * User Repository -
 * used to provide all data of App User
 *
 * @param userDao the Dao to use with this repository.
 */
class UserRepository @Inject constructor(private val userDao: UserDtoDao) {


    /**
     * Method to get the user by his id.
     *
     * @param userId the user ID to get
     * @return a Flow of the User dto object.
     *
     */
    fun getUser(userId:Long): Flow<UserDto?> {
        return userDao.getUserById(userId)
    }


    /**
     * Method to add an new user.
     *
     * @param user  the new user object to add.
     * @param userId (optional) the custom id to use for the new user.
     *
     */
    suspend fun addUser(user: User, userId: Long? = null) {
        userDao.insertUser(user.toDto(userId))
    }


}