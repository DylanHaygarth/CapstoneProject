package com.example.capstoneproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Schedule")
data class ScheduledWorkout (
    @ColumnInfo(name = "workoutName")
    val workoutName: String,

    @ColumnInfo(name = "day")
    val day: String,

    @ColumnInfo(name = "startHour")
    val startHour: Int,

    @ColumnInfo(name = "startMin")
    val startMin: Int,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
)