package com.example.capstoneproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weeklyFoods")
data class EatenFood (
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "calories")
    val calories: Double,

    @ColumnInfo(name = "fats")
    val fats: Double,

    @ColumnInfo(name = "carbohydrates")
    val carbohydrates: Double,

    @ColumnInfo(name = "protein")
    val protein : Double,

    @ColumnInfo(name = "day")
    val day : String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
)