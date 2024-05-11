package com.example.fatchum2

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    // For Firebase Account Authentication
    private lateinit var auth: FirebaseAuth

    // For Remember me Shared Preferences
    private lateinit var emailText: EditText
    private lateinit var pwdText: EditText
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Save Email and Password
        emailText = findViewById(R.id.editEmail)
        pwdText = findViewById(R.id.editPassword)
        sf = getSharedPreferences("rememberMe", MODE_PRIVATE)
        editor = sf.edit()
    }

    private fun login(){
        val email = findViewById<EditText>(R.id.editEmail)
        val password = findViewById<EditText>(R.id.editPassword)
        val info = findViewById<TextView>(R.id.tvInfoLogin)

        // Check Null Input
        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Email or Password must not be empty.", Toast.LENGTH_SHORT).show()
            info.text = "Email and password must not be empty."
            return
        } else{
            val emailCheck = email.text.toString()
            val passwordCheck = password.text.toString()

            // If Remember Me is on

            auth.signInWithEmailAndPassword(emailCheck, passwordCheck)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        info.text = "Invalid email or password."
                    }
                }
        }
    }

    override fun onPause() {
        super.onPause()

        val remember = findViewById<CheckBox>(R.id.checkbox)
        val mail = emailText.text.toString()
        val pwd = pwdText.text.toString()
        editor.apply{
            putString("sf_email", mail)
            putString("sf_pwd", pwd)
            putString("sf_remember", "${remember.isChecked}")
            commit()
        }
    }

    override fun onResume() {
        super.onResume()

        val status = sf.getString("sf_remember", null)
        if (status == "true"){
            val remember = findViewById<CheckBox>(R.id.checkbox)
            remember.isChecked = true

            val mail = sf.getString("sf_email", null)
            val pwd = sf.getString("sf_pwd", null)
            emailText.setText(mail)
            pwdText.setText(pwd)
        }
    }
}