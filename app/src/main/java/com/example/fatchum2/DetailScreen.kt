package com.example.fatchum2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DetailScreen: AppCompatActivity() {
    private lateinit var detailScreenAdapter: DetailScreenAdapter
    private lateinit var instructionDetailAdapter: InstructionDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)

        val imageView: ImageView = findViewById(R.id.imgItem)
        val imageUrl = "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/b89ca73d7e5743968f86c54d4fff2660/FB_05.jpg"

        Picasso.get().load(imageUrl).into(imageView)

        val stringList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        val recyclerView = findViewById<RecyclerView>(R.id.rvIngredients)
        detailScreenAdapter = DetailScreenAdapter(stringList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = detailScreenAdapter

        val instRecyclerView = findViewById<RecyclerView>(R.id.rvInstructions)
        instructionDetailAdapter = InstructionDetailAdapter(stringList)
        instRecyclerView.layoutManager = LinearLayoutManager(this)
        instRecyclerView.adapter = instructionDetailAdapter
    }
}