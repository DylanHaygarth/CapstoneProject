package com.example.capstoneproject.api

import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.model.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApiService {
    @GET("v1_1/search/{searchText}?fields=item_name%2Cbrand_name%2Cnf_calories%2Cnf_total_fat%2Cnf_protein%2Cnf_total_carbohydrate")
    suspend fun getFood(@Path("searchText") searchText: String, @Header("x-rapidapi-key") key: String, @Header("x-rapidapi-host") host: String) : FoodResponse
}
