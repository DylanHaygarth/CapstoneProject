package com.example.capstoneproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstoneproject.api.FoodApi
import com.example.capstoneproject.api.FoodApiService
import com.example.capstoneproject.model.FoodItem
import kotlinx.coroutines.withTimeout

class FoodRepository {
    private val foodApiService: FoodApiService = FoodApi.createApi()

    private val _searchedFoods: MutableLiveData<List<FoodItem>> = MutableLiveData()
    val searchedFoods: LiveData<List<FoodItem>>
        get() = _searchedFoods

    suspend fun getFoods(searchText: String, key: String, host: String) {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5000) {
                foodApiService.getFood(searchText, key, host)
            }

            val foods = arrayListOf<FoodItem>()
            for (i in result.results.indices) {
                foods.add(result.results[i].foodItem)
            }

            _searchedFoods.value = foods
        } catch (error: Throwable) {
        }
    }
}