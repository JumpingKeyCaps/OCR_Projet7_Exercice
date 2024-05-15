package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  User DTO -
 *  Data Class used by Room to create the user DataBase table and ease the CRUD operation on it.
 */

@Entity(tableName = "user")
data class UserDto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,


    @ColumnInfo(name = "nom")
    var nom: String,


    @ColumnInfo(name = "email")
    var email: String,


    @ColumnInfo(name = "password")
    var password: String,

    )