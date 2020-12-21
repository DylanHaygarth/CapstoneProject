package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class FoodItem (
    @SerializedName("item_name") val itemName : String,
    @SerializedName("brand_name") val brandName : String,
    @SerializedName("nf_calories") val calories : Double,
    @SerializedName("nf_total_fat") val fats : Double,
    @SerializedName("nf_total_carbohydrate") val carbohydrates : Double,
    @SerializedName("nf_protein") val proteins : Double,
    @SerializedName("nf_serving_size_qty") val servingQuantity : Long,
    @SerializedName("nf_serving_size_unit") val servingUnit : String
)

