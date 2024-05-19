package com.example.fatchum2

// data input that will be passed to server (the search inputs)
data class IngredientsInput(
    val ingredients: String
)

// data that gets fetched from server
data class Recipe(
    val image_url: String,
    val recipe_name: String,
    val ingredients: List<String>,
    val protein: String,
    val fat: String,
    val calories: String,
    val sugar: String,
    val carbohydrates: String,
    val fiber: String,
    val instructions: List<String>,
    val tags: String
)

