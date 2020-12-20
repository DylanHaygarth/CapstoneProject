package com.example.capstoneproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstoneproject.database.FitnessRoomDatabase
import com.example.capstoneproject.model.Profile
import com.example.capstoneproject.repository.FitnessRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class FitnessViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val fitnessRepository = FitnessRepository(application.applicationContext)

    val profile : LiveData<Profile?> = fitnessRepository.getProfile()

    private val _goalCalories: MutableLiveData<Int> = MutableLiveData()
    val goalCalories: LiveData<Int>
        get() = _goalCalories

    fun insertProfile(profile: Profile) {
        ioScope.launch {
            fitnessRepository.insertProfile(profile)
        }
    }

    fun updateProfile(gender: String, age: Int, height: Int, weight: Int, activityLevel: String, goalAction: String, goalWeight: Int, goalTime: Int) {
        val newProfile = Profile(
            id = profile.value?.id,
            gender = gender,
            age = age,
            height = height,
            weight = weight,
            activityLevel = activityLevel,
            goalAction = goalAction,
            goalWeight = goalWeight,
            goalTime = goalTime
        )

        ioScope.launch {
            fitnessRepository.updateProfile(newProfile)
        }
    }

    fun setGoalCalories(calories: Int) {
        _goalCalories.value = calories
    }

    fun deleteProfile() {
        ioScope.launch {
            fitnessRepository.deleteProfile()
        }
    }
}