package com.example.capstoneproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.capstoneproject.dao.FoodDao
import com.example.capstoneproject.dao.ScheduleDao
import com.example.capstoneproject.database.FitnessRoomDatabase
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.ScheduledWorkout

class ScheduleRepository(context: Context) {
    private var scheduleDao: ScheduleDao

    init{
        val fitnessRoomDatabase = FitnessRoomDatabase.getDatabase(context)
        scheduleDao = fitnessRoomDatabase!!.scheduleDao()
    }

    fun getAllWorkouts() : LiveData<List<ScheduledWorkout>> {
        return scheduleDao.getAllWorkouts()
    }

    suspend fun insertWorkout(scheduledWorkout: ScheduledWorkout) {
        scheduleDao.insertWorkout(scheduledWorkout)
    }

    suspend fun deleteWorkout(scheduledWorkout: ScheduledWorkout) {
        scheduleDao.deleteWorkout(scheduledWorkout)
    }

    suspend fun deleteAll() {
        scheduleDao.deleteAll()
    }
}