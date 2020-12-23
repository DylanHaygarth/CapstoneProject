package com.example.capstoneproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import com.example.capstoneproject.dao.FitnessDao
import com.example.capstoneproject.dao.FoodDao
import com.example.capstoneproject.database.FitnessRoomDatabase
import com.example.capstoneproject.model.EatenFood

class EatenFoodRepository(context: Context) {
    private var foodDao: FoodDao

    init{
        val fitnessRoomDatabase = FitnessRoomDatabase.getDatabase(context)
        foodDao = fitnessRoomDatabase!!.foodDao()
    }

    fun getAllFoods() : LiveData<List<EatenFood>> {
        return foodDao.getAllFoods()
    }

    suspend fun insertFood(eatenFood: EatenFood) {
        foodDao.insertFood(eatenFood)
    }

    suspend fun deleteFood(eatenFood: EatenFood) {
        foodDao.deleteFood(eatenFood)
    }
}