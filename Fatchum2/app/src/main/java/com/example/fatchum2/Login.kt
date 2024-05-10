package com.example.fatchum2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    // For Firebase Account Authentication
    private lateinit var auth: FirebaseAuth

    // For Remember me Shared Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Firebase Auth
        auth = Firebase.auth

        //Save Email and Password
    }
}