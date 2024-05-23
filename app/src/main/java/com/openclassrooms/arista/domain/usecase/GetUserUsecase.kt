package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case  to Get the User data object.
 */
class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Method to get the current user from the User Repository.
     *
     * @return a User Object
     **/
     fun execute(): Flow<UserDto?> {
        return userRepository.getUser()
    }

    /**
     * Method to get the current user via the user ID.
     *
     * @param id the user id to load
     * @return a User Object
     **/
     fun execute(id: Long): Flow<UserDto?> {
        return userRepository.getUserById(id)
    }
}