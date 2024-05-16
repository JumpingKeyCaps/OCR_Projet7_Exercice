package com.openclassrooms.arista.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * The Database of the App (Room)
 */
@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDtoDao(): UserDtoDao
    abstract fun sleepDtoDao(): SleepDtoDao
    abstract fun exerciseDtoDao(): ExerciseDtoDao


    /**
     *  Callback to prepopulate the DB with basic dataset.
     *  (called from onCreate())
     * @param scope the coroutine scope to use.
     * @return the callback method.
     */
    private class AppDatabaseCallback(private val scope: CoroutineScope) : Callback(){
        override fun onCreate(db: SupportSQLiteDatabase){
            super.onCreate(db)
            INSTANCE?.let{ database ->
                scope.launch {
                    populateDatabase(database.sleepDtoDao(), database.userDtoDao())
                }
            }
        }

    }





    /**
     * Companion object to get a single instance of the database (singleton)
     */
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Method to get the database instance.
         * @param context the used context.
         * @param coroutineScope the scope to use with the coroutine.
         * @return the AppDataBase instance to use.
         */
        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }

        }

        /**
         * Method to populate the database instance.
         * @param sleepDao the sleep Dao to use.
         * @param userDao the user Dao to use.
         */
        suspend fun populateDatabase(sleepDao: SleepDtoDao, userDao: UserDtoDao) {


            //Prepopulate with Sleep
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 480, quality = 4
                )
            )
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 450, quality = 3
                )
            )


            //Prepopulate with a default User

            userDao.insertUser(
                UserDto(
                    nom = "John Dob", email = "johndob@test.test", password = "XCweqcuinwuweiu324nedfis23uin"
                )
            )




        }
    }





}