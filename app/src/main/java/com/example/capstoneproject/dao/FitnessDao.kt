package com.example.capstoneproject.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.capstoneproject.model.Profile

@Dao
interface FitnessDao {
    @Query("SELECT * FROM profileTable LIMIT 1")
    fun getProfile(): LiveData<Profile?>

    @Insert
    suspend fun insertProfile(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)

    @Query("DELETE FROM profileTable")
    suspend fun deleteProfile()
}