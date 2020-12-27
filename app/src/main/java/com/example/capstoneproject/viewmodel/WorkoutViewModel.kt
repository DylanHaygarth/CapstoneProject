package com.example.capstoneproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.model.Workout
import com.example.capstoneproject.repository.EatenFoodRepository
import com.example.capstoneproject.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository = WorkoutRepository(application.applicationContext)

    val workouts: LiveData<List<Workout>> = workoutRepository.getAllWorkouts()

    private val _selectedExercises: MutableLiveData<ArrayList<Exercise>> = MutableLiveData()
    val selectedExercises: LiveData<ArrayList<Exercise>>
        get() = _selectedExercises
    private val selectedExercisesTracker = arrayListOf<Exercise>()

    private val _clickedExercises: MutableLiveData<ArrayList<Exercise>> = MutableLiveData()
    val clickedExercises: LiveData<ArrayList<Exercise>>
        get() = _clickedExercises
    private val clickedExercisesTracker = arrayListOf<Exercise>()

    private val _workoutName: MutableLiveData<String> = MutableLiveData()
    val workoutName: LiveData<String>
        get() = _workoutName

    fun getAllWorkouts() {
        viewModelScope.launch {
            workoutRepository.getAllWorkouts()
        }
    }

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.insertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    fun deleteAllWorkouts() {
        viewModelScope.launch {
            workoutRepository.deleteAll()
        }
    }

    fun addExerciseToSelectedExercises(exercise: Exercise) {
        selectedExercisesTracker.add(exercise)
        _selectedExercises.value = selectedExercisesTracker
    }

    fun removeSelectedExercises() {
        selectedExercisesTracker.clear()
        _selectedExercises.value = selectedExercisesTracker
    }

    fun setWorkoutName(name: String) {
        _workoutName.value = name
    }

    fun removeWorkoutName() {
        _workoutName.value = ""
    }

    fun addClickedExercises(exercises: List<Exercise>) {
        clickedExercisesTracker.clear()
        clickedExercisesTracker.addAll(exercises)
        _clickedExercises.value = clickedExercisesTracker
    }
}