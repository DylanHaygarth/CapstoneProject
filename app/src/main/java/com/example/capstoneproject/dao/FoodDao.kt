package com.example.capstoneproject.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.capstoneproject.model.EatenFood

@Dao
interface FoodDao {
    @Query("SELECT * FROM weeklyFoods")
    fun getAllFoods(): LiveData<List<EatenFood>>

    @Insert
    suspend fun insertFood(eatenFood: EatenFood)

    @Delete
    suspend fun deleteFood(eatenFood: EatenFood)
}