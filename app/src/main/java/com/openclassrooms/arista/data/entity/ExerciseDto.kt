package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 *  Exercise DTO -
 *  Data Class used by Room to create the exercise DataBase table and ease the CRUD operation on it.
 */

@Entity(tableName = "exercise")
data class ExerciseDto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,


    @ColumnInfo(name = "start_time")
    var startTime: Long,


    @ColumnInfo(name = "duration")
    var duration: Int,


    @ColumnInfo(name = "category")
    var category: String,


    @ColumnInfo(name = "intensity")
    var intensity: Int

)
