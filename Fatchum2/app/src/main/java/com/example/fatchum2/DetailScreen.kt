package com.example.fatchum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class DetailScreen : AppCompatActivity() {
    private lateinit var detailScreenAdapter: DetailScreenAdapter
    private lateinit var instructionDetailAdapter: InstructionDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        val values = intent.getParcelableExtra<Recipe>("RecipeInformation")
        if (values != null) {
            Log.i("MyTAG", "RECIPE VALUES: ${values.instructions}")
            Log.i("MyTAG", "RECIPE VALUES: ${values.instructions[0]}")
        }

        val imageView: ImageView = findViewById(R.id.imgItem)
        val imageUrl = values?.image_url

        Picasso.get().load(imageUrl).into(imageView)

//        val stringList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        val recyclerView = findViewById<RecyclerView>(R.id.rvIngredients)
        if (values != null) {
            detailScreenAdapter = DetailScreenAdapter(values.ingredients)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = detailScreenAdapter

        // NUTRITION FACTS
        val proteinData = findViewById<TextView>(R.id.proteinData)
        val fatData = findViewById<TextView>(R.id.fataData)
        val calorieData = findViewById<TextView>(R.id.calorieData)
        val sugarData = findViewById<TextView>(R.id.sugarData)
        val carbohydratesData = findViewById<TextView>(R.id.carboData)
        val fiberData = findViewById<TextView>(R.id.fiberData)

        proteinData.setText(values?.protein)
        fatData.setText(values?.fat)
        calorieData.setText(values?.calories)
        sugarData.setText(values?.sugar)
        carbohydratesData.setText(values?.carbohydrates)
        fiberData.setText(values?.fiber)


        val instRecyclerView = findViewById<RecyclerView>(R.id.rvInstructions)

        instructionDetailAdapter = values?.instructions?.let { InstructionDetailAdapter(it) }!!


        instRecyclerView.layoutManager = LinearLayoutManager(this)
        instRecyclerView.adapter = instructionDetailAdapter

    }
}