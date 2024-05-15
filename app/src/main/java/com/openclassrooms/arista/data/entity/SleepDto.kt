package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Sleep DTO -
 *  Data Class used by Room to create the sleep DataBase table and ease the CRUD operation on it.
 */

@Entity(tableName = "sleep")
data class SleepDto (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,


    @ColumnInfo(name = "start_time")
    var startTime: Long,


    @ColumnInfo(name = "duration")
    var duration: Int,


    @ColumnInfo(name = "quality")
    var quality: Int,



)