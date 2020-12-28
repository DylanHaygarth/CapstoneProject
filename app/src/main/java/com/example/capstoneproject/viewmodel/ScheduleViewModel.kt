package com.example.capstoneproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.ScheduledWorkout
import com.example.capstoneproject.repository.ScheduleRepository
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val scheduleRepository = ScheduleRepository(application.applicationContext)

    val scheduledWorkouts: LiveData<List<ScheduledWorkout>> = scheduleRepository.getAllWorkouts()

    fun insertWorkout(scheduledWorkout: ScheduledWorkout) {
        viewModelScope.launch {
            scheduleRepository.insertWorkout(scheduledWorkout)
        }
    }

    fun deleteWorkout(scheduledWorkout: ScheduledWorkout) {
        viewModelScope.launch {
            scheduleRepository.deleteWorkout(scheduledWorkout)
        }
    }

    fun deleteAllWorkouts() {
        viewModelScope.launch {
            scheduleRepository.deleteAll()
        }
    }
}