package com.example.capstoneproject.ui

import androidx.room.TypeConverter
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.model.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    private var gson = Gson()

    @TypeConverter
    fun stringToExercisesList(data: String?): List<Exercise?>? {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType: Type = object : TypeToken<List<Exercise?>?>() {}.type

        return gson.fromJson<List<Exercise?>>(data, listType)
    }

    @TypeConverter
    fun exercisesListToString(someObjects: List<Exercise?>?): String? {
        return gson.toJson(someObjects)
    }
}