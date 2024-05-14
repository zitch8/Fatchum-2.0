package com.example.fatchum2

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.View
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
    private lateinit var info: TextView

    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val loginButton = findViewById<Button>(R.id.btnLogin)
        val register = findViewById<TextView>(R.id.tvRegister)
        
        // Initialize Firebase Auth
        auth = Firebase.auth

        //Save Email and Password
        emailText = findViewById(R.id.editEmail)
        pwdText = findViewById(R.id.editPassword)
        info = findViewById<TextView>(R.id.tvInfoLogin)
        sf = getSharedPreferences("rememberMe", MODE_PRIVATE)
        editor = sf.edit()

        // Register Click Listener
        register.setOnClickListener {
            val openRegister = Intent(this@Login, Register::class.java)
            startActivity(openRegister)
        }
        //Login Button Click Listener
        loginButton.setOnClickListener { login() }

        // Remove Info Text When Edit Text is Clicked
        val onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                info.text = ""
            }
        }
        emailText.onFocusChangeListener = onFocusChangeListener
        pwdText.onFocusChangeListener = onFocusChangeListener

    }

    private fun login(){
        // Check Null Input
        if (emailText.text.isEmpty() || pwdText.text.isEmpty()){
            Toast.makeText(this, "Email or Password must not be empty.", Toast.LENGTH_SHORT).show()
            info.text = "Email and password must not be empty."
            return
        } else{
            val emailCheck = emailText.text.toString()
            val passwordCheck = pwdText.text.toString()


            auth.signInWithEmailAndPassword(emailCheck, passwordCheck)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                        val openMain = Intent(this@Login, Main::class.java)
                        startActivity(openMain)
                        finish()
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