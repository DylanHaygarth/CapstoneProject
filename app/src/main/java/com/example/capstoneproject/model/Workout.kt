package com.example.capstoneproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Workouts")
data class Workout (
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "exercises")
    val exercises: List<Exercise>,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
)
