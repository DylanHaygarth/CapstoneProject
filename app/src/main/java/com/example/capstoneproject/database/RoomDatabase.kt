package com.example.capstoneproject.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.capstoneproject.dao.FitnessDao
import com.example.capstoneproject.model.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class FitnessRoomDatabase : RoomDatabase() {
    abstract fun fitnessDao(): FitnessDao

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