package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class FoodResponse (
    @SerializedName("hits") val results : List<FoodLists>
)

data class FoodLists (
    @SerializedName("fields") val foodItem: FoodItem
)