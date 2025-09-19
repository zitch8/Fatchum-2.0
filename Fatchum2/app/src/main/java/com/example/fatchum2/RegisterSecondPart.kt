package com.example.fatchum2

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RegisterSecondPart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_register_two)

        // DETECT BACK EXECUTION
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                Log.i("MYTAG", "BACK PRESSED")
                finish()
                true // Return true to indicate that the event was handled
            } else {
                false // Return false to pass the event to other handlers
            }
        }


    }
}