package com.example.fatchum2

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchArea : AppCompatActivity(), SearchResultAdapter.OnItemClickListener {

    private lateinit var searchResultsAdapter: SearchResultAdapter
    private val searchResults = mutableListOf<String>()
    private val selectedList = mutableListOf<String>()
    private lateinit var selectedIngredientsContainer: LinearLayout
    private val recommendedSearch = RecommendedSearch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_area)

        val searchBar = findViewById<EditText>(R.id.etSearchBar)
        val recyclerView = findViewById<RecyclerView>(R.id.rvSuggestions)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnFind = findViewById<Button>(R.id.btnFind)
        selectedIngredientsContainer = findViewById(R.id.selectedIngredientsContainer)
        searchBar.requestFocus()

        btnAdd.setOnClickListener {
            val ingredient = searchBar.text.toString()
            if (ingredient.isNotEmpty() && !selectedList.contains(ingredient)) {
                addIngredient(ingredient)
                searchBar.setText("")
            }
        }

        btnFind.setOnClickListener {
            val joinList = selectedList.joinToString(" ")
            val joinListComma = selectedList.joinToString(", ")
            Log.i("MyTAG", joinList)
//            val test = "chicken cheese"
            val ingredientsInput = IngredientsInput(joinList) // Initialize IngredientsInput
//            val ingredientsInput = IngredientsInput(test)
            recommendedSearch.callSearchAPI(
                ingredientsInput,
                onSuccess = { recipes ->
                        val intent = Intent(this@SearchArea, RecommendedSearch::class.java)
                        Log.d("MyTAG", "${ArrayList(recipes)}" )
                        intent.putParcelableArrayListExtra("recipes", ArrayList(recipes))
                        intent.putExtra("ingredients", joinListComma)
                        startActivity(intent)
                },
                onFailure = {
                    // Handle the failure case if needed
                    Log.e("MyTAG123", "Error Cannot fetch")
                }
            )
        }

        searchResultsAdapter = SearchResultAdapter(searchResults, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = searchResultsAdapter

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val query = p0.toString()
//                Log.i("MyTAG", p0.toString())
                // CREATE AN INSTANCE DATABASE HANDLER
                val dbHelper = DataBaseHandler(this@SearchArea)
                // GET READABLE DATABASE INSTANCE
                val db = dbHelper.readableDatabase
                var cursor: Cursor? = null

                try {
                    cursor = db.rawQuery("SELECT * FROM Ingredients WHERE IngredientName='${query}'", null)
                    val rowCount = cursor.count
                    if (rowCount > 0) {
                        btnAdd.setEnabled(true);
                    } else {
                        btnAdd.setEnabled(false);
                    }
                } catch (e: Exception) {
                    Log.e("MyTAG", "Error while searching database", e)
                } finally {
                    cursor?.close()
                }

                try {
                    // Use parameterized query to avoid SQL injection
                    cursor = db.rawQuery("SELECT * FROM Ingredients WHERE IngredientName LIKE ?", arrayOf("%$query%"))

                    // Clear previous search results
                    searchResults.clear()

                    // Check if the cursor contains results and get the index of the column
                    if (cursor.moveToFirst()) {
                        val colIngredientName = cursor.getColumnIndex("IngredientName")
                        do {
                            val ingredient = cursor.getString(colIngredientName)
                            searchResults.add(ingredient)
//                            Log.i("MyTAG", ingredient)
                        } while (cursor.moveToNext())
                    }
                    // Notify the adapter that data has changed
                    searchResultsAdapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Log.e("MyTAG", "Error while searching database", e)
                } finally {
                    cursor?.close()
                }
            }
        })

    }

    override fun onItemClick(ingredient: String) {
//        Toast.makeText(this, ingredient, Toast.LENGTH_SHORT).show()
        val searchBar = findViewById<EditText>(R.id.etSearchBar)
        searchBar.setText(ingredient)
    }

    private fun addIngredient(ingredient: String) {
        selectedList.add(ingredient)
        val inflater = LayoutInflater.from(this)

        val cardView = inflater.inflate(R.layout.ingredient_card, selectedIngredientsContainer, false) as CardView
        val textView = cardView.findViewById<TextView>(R.id.textView5)
        val imageButton = cardView.findViewById<ImageButton>(R.id.imageButton)

        textView.text = ingredient
        imageButton.setOnClickListener {
            selectedIngredientsContainer.removeView(cardView)
            selectedList.remove(ingredient)
        }

        selectedIngredientsContainer.addView(cardView)
    }


}