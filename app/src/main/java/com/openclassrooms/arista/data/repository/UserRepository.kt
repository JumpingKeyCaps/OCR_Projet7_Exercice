package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

class UserRepository(private val userDao: UserDtoDao) {

    // Get the current user
    suspend fun getUser(): User? {
        return try{
            userDao.getAllUser().first().let { User.fromDto(it[0]) }
        }catch (e: Exception){
            null
        }
    }


    // Add a new user
    suspend fun addUser(user: User) {
        userDao.insertUser(user.toDto())
    }



}