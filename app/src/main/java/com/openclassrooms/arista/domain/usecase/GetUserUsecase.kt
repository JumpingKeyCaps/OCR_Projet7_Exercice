package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.firstOrNull
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
    suspend fun execute(): User? {
        return userRepository.getUser().firstOrNull()
    }
}