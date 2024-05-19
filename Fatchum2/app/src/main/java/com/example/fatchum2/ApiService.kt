package com.example.fatchum2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/search")
    fun postIngredients(@Body ingredientsInput: IngredientsInput): Call<List<Recipe>>
}