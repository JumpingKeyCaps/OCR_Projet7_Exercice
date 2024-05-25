package com.openclassrooms.arista.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.data.DefaultUserConfig.Companion.USER_ID
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the User Data.
 */
@HiltViewModel
class UserDataViewModel @Inject constructor(private val getUserUsecase: GetUserUsecase) :
    ViewModel() {
    private val _userFlow = MutableStateFlow<User?>(null)
    val userFlow: StateFlow<User?> = _userFlow.asStateFlow()

    init {
        //Load user Profile
        loadUserData(USER_ID)   // load the default user profile for the default config USER_ID

    }


    /**
     * Method to load the user data with an ID (or the 1st user in bdd by default).
     * @param id  the id of the user, can be void (null by default).
     */
    private fun loadUserData(id: Long) { // id is an optional parameter
        viewModelScope.launch(Dispatchers.IO) {
            val flow = getUserUsecase.execute(id)

            // Get the User from the use case returned flow.
            flow.collect { userDto ->
                val user = userDto?.let { User.fromDto(it) }
                _userFlow.value = user // update the stateflow for the fragment
            }
        }
    }







}
