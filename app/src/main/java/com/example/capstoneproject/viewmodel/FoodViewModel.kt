package com.example.capstoneproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.repository.FitnessRepository
import com.example.capstoneproject.repository.FoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val foodRepository = FoodRepository()

    val foods: LiveData<List<FoodItem>> = foodRepository.foods

    fun getFoods(searchText: String, key: String, host: String) {
        viewModelScope.launch {
            foodRepository.getFoods(searchText, key, host)
        }
    }
}