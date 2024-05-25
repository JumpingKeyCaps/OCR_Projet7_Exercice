package com.openclassrooms.arista.domain.model
import com.openclassrooms.arista.data.entity.UserDto

data class User(
    var id: Long,
    var name: String,
    var email: String,
    var password: String){



    /**
     * Methode to convert an User object to an User Dto
     * @param userId (optional) to set a custom Id for the user
     * @return an userDto
     */
    fun toDto(userId: Long? = null): UserDto {
      return if(userId == null){
          UserDto(nom = this.name, email = this.email, password = this.password)
      }else{
          UserDto(id = this.id, nom = this.name, email = this.email, password = this.password)
      }
    }


    companion object {
        /**
         * Methode to convert an userDto to an user object
         * @param dto the user dto to convert in user object
         * @return an user object.
         */
        fun fromDto(dto: UserDto): User {
            return User(dto.id,dto.nom,dto.email,dto.password)
        }
    }


}