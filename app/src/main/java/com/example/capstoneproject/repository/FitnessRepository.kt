package com.example.capstoneproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.capstoneproject.dao.FitnessDao
import com.example.capstoneproject.database.FitnessRoomDatabase
import com.example.capstoneproject.model.Profile

class FitnessRepository(context: Context) {
    private var fitnessDao: FitnessDao

    init{
        val fitnessRoomDatabase = FitnessRoomDatabase.getDatabase(context)
        fitnessDao = fitnessRoomDatabase!!.fitnessDao()
    }

    fun getProfile() : LiveData<Profile?> {
        return fitnessDao.getProfile()
    }

    suspend fun insertProfile(profile: Profile) {
        fitnessDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        fitnessDao.updateProfile(profile)
    }

    suspend fun deleteProfile() {
        fitnessDao.deleteProfile()
    }
}