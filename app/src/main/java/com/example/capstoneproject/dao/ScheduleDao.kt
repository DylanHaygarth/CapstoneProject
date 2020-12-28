package com.example.capstoneproject.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.capstoneproject.model.ScheduledWorkout

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule")
    fun getAllWorkouts(): LiveData<List<ScheduledWorkout>>

    @Insert
    suspend fun insertWorkout(scheduledWorkout: ScheduledWorkout)

    @Delete
    suspend fun deleteWorkout(scheduledWorkout: ScheduledWorkout)

    @Query("DELETE FROM Schedule")
    suspend fun deleteAll()
}