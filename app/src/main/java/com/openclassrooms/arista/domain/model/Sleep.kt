package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class Sleep(
    @JvmField var startTime: LocalDateTime,
    var duration: Int,
    var quality: Int){
    /**
     * Methode to convert an sleep object to an sleep Dto
     * @return an sleepDto
     */
    fun toDto(userId: Long): SleepDto {
        //convert startime from LocalDateTime to  a Long
        val startTimeInMillis = (startTime.atZone(ZoneId.systemDefault()).toInstant()).toEpochMilli()
        return SleepDto(
            startTime = startTimeInMillis,
            duration = this.duration,
            quality = this.quality,
            ownerId = userId
        )
    }




    companion object {
        /**
         * Methode to convert a SleepDao to an sleep object
         * @param dto the sleep dto to convert in sleep object
         * @return an sleep object.
         */
        fun fromDto(dto: SleepDto): Sleep {
            val instant = Instant.ofEpochMilli(dto.startTime)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            return Sleep(localDateTime, dto.duration, dto.quality)
        }




    }
}
