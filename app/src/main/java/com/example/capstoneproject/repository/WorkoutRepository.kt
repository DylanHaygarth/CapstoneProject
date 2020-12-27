package com.example.capstoneproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.capstoneproject.dao.FoodDao
import com.example.capstoneproject.dao.WorkoutDao
import com.example.capstoneproject.database.FitnessRoomDatabase
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.Workout

class WorkoutRepository(context: Context) {
    private var workoutDao: WorkoutDao

    init{
        val fitnessRoomDatabase = FitnessRoomDatabase.getDatabase(context)
        workoutDao = fitnessRoomDatabase!!.workoutDao()
    }

    fun getAllWorkouts() : LiveData<List<Workout>> {
        return workoutDao.getAllWorkouts()
    }

    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun deleteAll() {
        workoutDao.deleteAll()
    }
}