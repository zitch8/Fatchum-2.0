package com.example.fatchum2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendedSearch: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_search)

        val etRSearchBar = findViewById<EditText>(R.id.etRSearchBar)
        val backButton = findViewById<ImageButton>(R.id.back_button)

        etRSearchBar.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus){
                this.onBackPressed()
                finish()
            }
        }
        backButton.setOnClickListener{
            this.onBackPressed()
            finish()
//            startActivity(Intent(this@RecommendedSearch, DetailScreen::class.java))
        }

        // Sample input for /recommend api

        // Sample input for /search api
        val ingredientsInput = IngredientsInput("chicken garlic onion")
        callSearchAPI(ingredientsInput)

        val tagsInput = TagsInput(arrayOf(868, 6124, 3094))
        callRecommendAPI(tagsInput)


    }

    // to call /search API
    fun callSearchAPI(ingredientsInput: IngredientsInput){

        // pass input to /search api
        RetrofitClient.instance.postIngredients(ingredientsInput).enqueue(object :
            Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    recipes?.forEach { recipe ->
                        Log.d("Recipe", "Name: ${recipe.recipe_name}, : ${recipe.ingredients}")
                    }

                    if (recipes != null) {
                        val filter = recipes.filter { it.recipe_name == "Garlic Brown Sugar Chicken" }
                        if (filter.isNotEmpty()) {
                            filter.forEach { recipe ->
                                Log.d("Test123", """
                        Name: ${recipe.recipe_name}
                        Image URL: ${recipe.image_url}
                        Ingredients: ${recipe.ingredients.joinToString(", ")}
                        Protein: ${recipe.protein}
                        Fat: ${recipe.fat}
                        Calories: ${recipe.calories}
                        Sugar: ${recipe.sugar}
                        Carbohydrates: ${recipe.carbohydrates}
                        Fiber: ${recipe.fiber}
                        Instructions: ${recipe.instructions}
                        Tags: ${recipe.tags}
                    """.trimIndent())
                            }
                        } else {
                            Log.d("Test123", "No recipes found with the name 'Garlic Brown Sugar Chicken'")
                        }
                    } else {
                        Log.d("Test123", "recipes list is null")
                    }
                } else {
                    Log.e("Error", "Failed to get response")
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.e("Error", "Network request failed2", t)
            }
        })
    }


    fun callRecommendAPI(tagsInput: TagsInput){
        // pass input to /recommend api
        RetrofitClient.instance.postRecommendations(tagsInput).enqueue(object :
            Callback<List<Recommendation>> {
            override fun onResponse(call: Call<List<Recommendation>>, response: Response<List<Recommendation>>) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    recipes?.forEach { recipe ->
                        Log.d("Recipe", "Name: ${recipe.recipe_name}, : ${recipe.ingredients}")
                    }
                } else {
                    Log.e("Error", "Failed to get response")
                }
            }

            override fun onFailure(call: Call<List<Recommendation>>, t: Throwable) {
                Log.e("Error", "Network request failed", t)
            }
        })
    }

    // transforming instructions to array of string instead of array
    fun string_to_list(instructions: String): List<String> {
        val cleanedRecipeString = instructions.substring(1, instructions.length - 1)
        val instructionsArray = cleanedRecipeString.split("', '").map { it.trim('\'') }
        return instructionsArray
    }


}