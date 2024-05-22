package com.example.fatchum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class DetailScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        val values = intent.getParcelableExtra<Recipe>("RecipeInformation")
        if (values != null) {
            Log.i("MyTAG", "RECIPE VALUES: ${values.image_url}")
        }

    }
}