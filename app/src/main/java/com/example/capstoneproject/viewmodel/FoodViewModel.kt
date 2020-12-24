package com.example.capstoneproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.repository.EatenFoodRepository
import com.example.capstoneproject.repository.FitnessRepository
import com.example.capstoneproject.repository.FoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val foodRepository = FoodRepository()
    private val eatenFoodRepository = EatenFoodRepository(application.applicationContext)

    val searchedFoods: LiveData<List<FoodItem>> = foodRepository.searchedFoods
    val eatenFoods: LiveData<List<EatenFood>> = eatenFoodRepository.getAllFoods()

    private val _selectedFood: MutableLiveData<EatenFood> = MutableLiveData()
    val selectedFood: LiveData<EatenFood>
        get() = _selectedFood

    fun getSearchedFoods(searchText: String, key: String, host: String) {
        viewModelScope.launch {
            foodRepository.getFoods(searchText, key, host)
        }
    }

    fun insertFood(eatenFood: EatenFood) {
        viewModelScope.launch {
            eatenFoodRepository.insertFood(eatenFood)
        }
    }

    fun deleteFood(eatenFood: EatenFood) {
        viewModelScope.launch {
            eatenFoodRepository.deleteFood(eatenFood)
        }
    }

    fun deleteAllFoods() {
        viewModelScope.launch {
            eatenFoodRepository.deleteAll()
        }
    }

    fun setCurrentFood(food: EatenFood) {
        _selectedFood.value = food
    }
}