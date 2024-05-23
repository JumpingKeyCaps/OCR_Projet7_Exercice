package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * User Repository -
 * used to provide all data of App User
 *
 * @param userDao the Dao to use with this repository.
 */
class UserRepository @Inject constructor(private val userDao: UserDtoDao) {

    /**
     * Method to get the current user.
     *
     * use flatMap to handle the Flow of Users list returned by userDao
     * Inside flatMap, we convert the list to a flow (asFlow())
     * Then, we map each user object within the Flow to a User? object using User.fromDto(it)
     *
     * This will result in a Flow that emits the user objects from the lists retrieved from the DAO
     *
     * @return a Flow of the User object.
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUser(): Flow<User?> {
        return userDao.getAllUser().flatMapConcat { userList ->
            userList.asFlow().map { User.fromDto(it) }
        }
    }


    /**
     * Method to add an new user.
     *
     * @param user  the new user object to add.
     *
     */
    suspend fun addUser(user: User) {
        userDao.insertUser(user.toDto())
    }


}