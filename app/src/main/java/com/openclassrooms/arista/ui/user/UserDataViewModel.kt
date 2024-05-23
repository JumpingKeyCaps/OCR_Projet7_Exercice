package com.openclassrooms.arista.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        //Choose your flavor ...

        // loadUserData()     // return the default user profile
         loadUserData(5)   // return the custom user profile from his ID
        // loadUserData(999)   // if wrong id, return the default user profile

    }


    /**
     * Method to load the user data with an ID (or the 1st user in bdd by default).
     * @param id the id of the user, can be void (null by default).
     */
    private fun loadUserData(id: Long? = null) { // id is an optional parameter
        viewModelScope.launch(Dispatchers.IO) {
            val flow = if (id != null) {
                getUserUsecase.execute(id) // Use execute(id) for specific ID
            } else {
                getUserUsecase.execute() // Use execute() for default current user
            }

            // Get the User from the use case returned flow.
            flow.collect { userDto ->
                val user = userDto?.let { User.fromDto(it) }
                //check if the returned user is null,then call himself in default mode, else update the flow.
                if (user==null) loadUserData() else _userFlow.value = user // update the stateflow for the fragment
            }
        }
    }







}
