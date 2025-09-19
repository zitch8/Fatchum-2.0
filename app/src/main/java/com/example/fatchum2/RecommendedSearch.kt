package com.example.fatchum2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.squareup.picasso.Picasso

class RecommendedSearch : AppCompatActivity(), OnRecipeClickListener {

    private lateinit var nsvView: NestedScrollView
    private lateinit var rvRecipeList: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var adapter: AdapterRecipe
    private var recipes = mutableListOf<Recipe>()
    private var isLoading = false
    private var currentPage = 0
    private val pageSize = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_search)

        Log.i("MyTAG", "Started")

        val etRSearchBar = findViewById<EditText>(R.id.etRSearchBar)
        val backButton = findViewById<ImageButton>(R.id.back_button)

        etRSearchBar.setText(intent.extras?.getString("ingredients") ?: "No message found")

        // DISPLAY IMAGE


        etRSearchBar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                this.onBackPressed()
                finish()
            }
        }
        backButton.setOnClickListener {
            this.onBackPressed()
            finish()
        }

        // Append Recycle View
        nsvView = findViewById(R.id.nsvView)
        rvRecipeList = findViewById(R.id.rvContainer)
        pbLoading = findViewById(R.id.progressBar)

        rvRecipeList.layoutManager = LinearLayoutManager(this)
        adapter = AdapterRecipe(recipes, this)
        rvRecipeList.adapter = adapter

        // Retrieve the recipes from the intent
        val recipeList = intent.getParcelableArrayListExtra<Recipe>("recipes")
        recipeList?.forEach { recipe ->
            Log.d("Recipe", "Name: ${recipe.recipe_name}, Image URL: ${recipe.image_url}")
        }

        if (recipeList != null) {
            recipes.addAll(recipeList)
            if (recipes.size >= 26) {
                recipes.removeAt(25)
            }
            Log.d("MyTAG", "LENGTH: ${recipes.size}}")
            loadMoreRecipes()
        }

        nsvView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (!isLoading && scrollY == (nsvView.getChildAt(0).measuredHeight - nsvView.measuredHeight)) {
                isLoading = true
                pbLoading.visibility = View.VISIBLE

                // Simulate a delay before loading more recipes
                Handler(Looper.getMainLooper()).postDelayed({
                    loadMoreRecipes()
                    pbLoading.visibility = View.GONE // Hide the progress bar after loading
                    isLoading = false
                }, 2000) // Change this delay to your preferred value
            }
        }
    }

    private fun loadMoreRecipes() {
        Log.i("MyTAG", "GOING HERE 1")
        val start = currentPage * pageSize
        val end = minOf((currentPage + 1) * pageSize, recipes.size)
        if (start < end) {
            Log.i("MyTAG", "GOING HERE 2")
            val newRecipes = recipes.subList(start, end)
            if (newRecipes != null) {
                try {
                    adapter.addRecipes(newRecipes)
                    currentPage++
                } catch (e: Exception) {
                    Log.e("MyTAG", "Error adding recipes: ${e.message}")
                }
            } else {
                Log.e("MyTAG", "Error: newRecipes is null")
            }
        }
        Log.i("MyTAG", "GOING HERE 3")
    }

    override fun onRecipeClick(recipe: Recipe) {
        Toast.makeText(this, "Name: ${recipe.recipe_name} ${recipe.image_url}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@RecommendedSearch, DetailScreen::class.java)
        Log.d("MyTAG", "RECIPES:::::${recipe}" )
        intent.putExtra("RecipeInformation", recipe)
        startActivity(intent)
    }

    // to call /search API
    fun callSearchAPI(
        ingredientsInput: IngredientsInput,
        onSuccess: (List<Recipe>) -> Unit,
        onFailure: () -> Unit
    ) {
        Log.i("MyTAG", "$ingredientsInput")
        RetrofitClient.instance.postIngredients(ingredientsInput).enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    recipes?.forEach { recipe ->
                        Log.d("MyTag", "Name: ${recipe.recipe_name}, IMAG_URL: ${recipe.image_url} ")
                    }
                    if (recipes != null) {
                        onSuccess(recipes)
                    } else {
                        onFailure()
                    }
                } else {
                    Log.d("ABC", "Error")
                    onFailure()
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.d("DEF", "Error")
                onFailure()
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

    // transforming instructions to list of string instead of string
    fun string_to_list(instructions: String): List<String> {
        val cleanedRecipeString = instructions.substring(1, instructions.length - 1)
        val instructionsArray = cleanedRecipeString.split("', '").map { it.trim('\'') }
        return instructionsArray
    }


}