package com.example.fatchum2

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
        }

        // Sample input for /search api
        val ingredientsInput = IngredientsInput("chicken garlic onion")
        callSearchAPI(ingredientsInput)

        // Sample input for /recommend api
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
                } else {
                    Log.e("Error", "Failed to get response")
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.e("Error", "Network request failed", t)
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