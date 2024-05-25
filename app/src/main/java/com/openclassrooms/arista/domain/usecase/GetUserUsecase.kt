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
     * by default or by ID
     * @param id optional userid
     * @return a UserDto Object flow
     **/
    fun execute(id: Long): Flow<UserDto?> {
        return userRepository.getUser(id)
    }


}