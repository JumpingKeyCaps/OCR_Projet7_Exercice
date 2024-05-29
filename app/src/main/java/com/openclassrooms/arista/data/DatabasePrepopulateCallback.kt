package com.openclassrooms.arista.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * The Callback used to prepopulate the database.
 */
class DatabasePrepopulateCallback(private val coroutineScope:CoroutineScope) : RoomDatabase.Callback() {

    /**
     * Overriding the onCreate Methode to prepopulate the Database on his call.
     * @param db the database.
     */
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        coroutineScope.launch {
            prepopulateDatabase(db)
        }
    }

    /**
     * Method to Pre-populate the database.
     * @param db the database reference to populate.
     */
     private fun prepopulateDatabase(db: SupportSQLiteDatabase){
        //Start the database transaction
        db.beginTransaction()
        try {
            // Prepopulate with a default user
            val defaultUserId = db.insert("user",SQLiteDatabase.CONFLICT_IGNORE,
                ContentValues().apply{
                put("nom","Tom Default")
                put("email","tomdefault@test.test")
                put("password","SdRRTq7627u324ne099hqq90in")
            })
            // Prepopulate with a custom default user with a selected id.
            val customUserId = db.insert("user",SQLiteDatabase.CONFLICT_IGNORE,
                ContentValues().apply{
                    put("nom","John Custom")
                    put("email","CustomJhon@test.test")
                    put("password","Fefsfdtresfe4535sdfgs0in")
                    put("id",5)
                })

            // Prepopulate with Sleep sessions using the inserted default user ID
            db.insert(
                "sleep",
                SQLiteDatabase.CONFLICT_IGNORE,
                ContentValues().apply {
                    put("start_time", LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
                    put("duration", 480)
                    put("quality", 4)
                    put("owner_id", defaultUserId) // Use the inserted default user ID
                }
            )
            db.insert(
                "sleep",
                SQLiteDatabase.CONFLICT_IGNORE,
                ContentValues().apply {
                    put("start_time", LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
                    put("duration", 60)
                    put("quality", 1)
                    put("owner_id", defaultUserId) // Use the inserted default user ID
                }
            )
            // Prepopulate with Sleep sessions using the inserted custom user ID
            db.insert(
                "sleep",
                SQLiteDatabase.CONFLICT_IGNORE,
                ContentValues().apply {
                    put("start_time", LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
                    put("duration", 60)
                    put("quality", 1)
                    put("owner_id", customUserId) // Use the inserted custom user ID
                }
            )
            //transaction is all ok !
            db.setTransactionSuccessful()

        } catch (e: Exception) {
            // Handle potential exceptions during pre-population
        } finally {
            //Finish the transaction
            db.endTransaction()
        }

    }

}