package com.example.capstoneproject.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.capstoneproject.dao.FitnessDao
import com.example.capstoneproject.dao.FoodDao
import com.example.capstoneproject.dao.ScheduleDao
import com.example.capstoneproject.dao.WorkoutDao
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.Profile
import com.example.capstoneproject.model.ScheduledWorkout
import com.example.capstoneproject.model.Workout
import com.example.capstoneproject.ui.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Profile::class, EatenFood::class, Workout::class, ScheduledWorkout::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FitnessRoomDatabase : RoomDatabase() {
    abstract fun fitnessDao(): FitnessDao
    abstract fun foodDao(): FoodDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private const val DATABASE_NAME = "FITNESS_DATABASE"

        @Volatile
        private var instance: FitnessRoomDatabase? = null

        fun getDatabase(context: Context): FitnessRoomDatabase? {
            if (instance == null) {
                synchronized(FitnessRoomDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            FitnessRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}