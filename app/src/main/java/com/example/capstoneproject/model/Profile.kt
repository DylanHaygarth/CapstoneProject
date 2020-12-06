package com.example.capstoneproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "profileTable")
data class Profile (
    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "activityLevel")
    val activityLevel: String,

    @ColumnInfo(name = "goalAction")
    val goalAction: String,

    @ColumnInfo(name = "goalWeight")
    val goalWeight: Int,

    @ColumnInfo(name = "goalTime")
    val goalTime: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)