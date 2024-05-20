package com.example.fatchum2

// data input that will be passed to /search API
data class IngredientsInput(
    val ingredients: String
)

// data that will be passed to /recommend API
data class TagsInput(
    val tags: Array<Int>
)

// data that gets fetched from /search API
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
    val instructions: String,
    val tags: String
)

// data that gets fetched from /search API
data class Recommendation(
    val image_url: String,
    val recipe_name: String,
    val ingredients: List<String>,
    val protein: String,
    val fat: String,
    val calories: String,
    val sugar: String,
    val carbohydrates: String,
    val fiber: String,
    val instructions: String,
    val tags: String
)
