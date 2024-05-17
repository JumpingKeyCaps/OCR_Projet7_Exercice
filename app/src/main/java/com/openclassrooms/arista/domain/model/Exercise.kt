package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
/**
 *
 */
data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
){

    /**
     * Methode to convert an Exercise object to an Exercise Dto
     * @return an exerciseDto
     */
    fun toDto(): ExerciseDto {
        //convert startime from LocalDateTime to  a Long
        val startTimeInMillis = (startTime.atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli()
        return ExerciseDto(
            id = this.id ?: 0L,
            startTime = startTimeInMillis,
            duration = this.duration,
            category = this.category.toString(), // Convert ExerciseCategory to String
            intensity = this.intensity
        )
    }

    companion object {
        /**
         * Methode to convert an ExerciseDto to an Exercise object
         * @param dto the exercise dto to convert in exercise object
         * @return an Exercise object.
         */
        fun fromDto(dto: ExerciseDto): Exercise {
            val instant = Instant.ofEpochMilli(dto.startTime)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

            val category = try {
                ExerciseCategory.valueOf(dto.category)
            } catch (e: IllegalArgumentException) {
                // Handle invalid category string
                ExerciseCategory.Walking // set walking by dft
            }
            return Exercise(dto.id, localDateTime, dto.duration, category, dto.intensity)
        }
    }

}